    package co.istab.blooddonationservice.infrastructure.database.provider;

    import co.istab.blooddonationservice.domain.blood.notification.entity.Notification;
    import co.istab.blooddonationservice.domain.blood.notification.provider.NotificationDatabaseProvider;
    import co.istab.blooddonationservice.infrastructure.database.mapper.NotificationDatabaseMapper;
    import co.istab.blooddonationservice.infrastructure.database.mysql.Entity.NotificationEntity;
    import co.istab.blooddonationservice.infrastructure.database.mysql.repository.NotificationJpaRepository;
    import co.istab.blooddonationservice.share.entity.PaginationQuery;
    import co.istab.blooddonationservice.share.entity.Paging;
    import co.istab.blooddonationservice.share.utility.PageNumberUtility;
    import jakarta.persistence.criteria.JoinType;
    import jakarta.transaction.Transactional;
    import lombok.RequiredArgsConstructor;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.PageRequest;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.domain.Sort;
    import org.springframework.security.core.parameters.P;
    import org.springframework.stereotype.Service;

    import java.util.Optional;

    @Service
    @RequiredArgsConstructor
    public class NotificationDatabaseProviderFacade implements NotificationDatabaseProvider {

        private final NotificationJpaRepository notificationRepository;
        private final NotificationDatabaseMapper mapper;


        @Override
        public Paging<Notification> getAll(Integer userId, PaginationQuery query) {

            Page<NotificationEntity> page =
                    notificationRepository.findAll(
                            (root, q, cb) -> {
                                if (q.getResultType() != Long.class) {
                                    root.fetch("user", JoinType.LEFT);
                                }

                                return cb.and(
                                        cb.equal(root.get("user").get("id"), userId),
                                        cb.isNull(root.get("deletedAt"))
                                );
                            },
                            PageRequest.of(
                                    PageNumberUtility.in(query.getPage()),
                                    query.getSize(),
                                    Sort.by(Sort.Direction.DESC, "createdAt")
                            )
                    );

            return toPaging(page);
        }

        @Override
        @Transactional
        public Paging<Notification> getUnread(Integer userId, PaginationQuery query) {
            Pageable pageable = PageRequest.of(
                    PageNumberUtility.in(query.getPage()),
                    query.getSize(),
                    Sort.by(Sort.Direction.DESC, "createdAt")
            );

            Page<NotificationEntity> page = notificationRepository.getUnread(userId, pageable);

            return Paging.<Notification>builder()
                    .items(page.getContent().stream().map(mapper::from).toList())
                    .page(PageNumberUtility.out(page.getNumber()))
                    .size(page.getSize())
                    .totalPages(page.getTotalPages())
                    .total(page.getTotalElements())
                    .build();
        }

        @Override
        public Optional<Notification> getById(Integer id) {
            return notificationRepository.findOne(
                    (root, query, criteriaBuilder) -> {
                        root.fetch("user", JoinType.LEFT);

                        return criteriaBuilder.and(
                                criteriaBuilder.isNull(root.get("deletedAt")),
                                criteriaBuilder.equal(root.get("id"), id)
                        );
                    }
            ).map(mapper::from);
        }

        @Override
        public Notification save(Notification notification) {

            NotificationEntity entity = mapper.from(notification);
            notificationRepository.save(entity);
            return mapper.from(entity);
        }

        private Paging<Notification> toPaging(Page<NotificationEntity> page) {

            return Paging.<Notification>builder()
                    .items(page.getContent().stream().map(mapper::from).toList())
                    .page(PageNumberUtility.out(page.getNumber()))
                    .size(page.getSize())
                    .totalPages(page.getTotalPages())
                    .total(page.getTotalElements())
                    .build();
        }




    }
