package co.istab.blooddonationservice.infrastructure.database.provider;

import co.istab.blooddonationservice.domain.blood.notification.entity.ReadNotification;
import co.istab.blooddonationservice.domain.blood.notification.provider.ReadNotificationDatabaseProvider;
import co.istab.blooddonationservice.infrastructure.database.mapper.ReadNotificationDatabaseMapper;
import co.istab.blooddonationservice.infrastructure.database.mysql.Entity.ReadNotificationEntity;
import co.istab.blooddonationservice.infrastructure.database.mysql.repository.ReadNotificationJpaRepository;
import jakarta.persistence.criteria.JoinType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReadNotificationDatabaseProviderFacade implements ReadNotificationDatabaseProvider {

    private final ReadNotificationJpaRepository readNotificationJpaRepository;
    private final ReadNotificationDatabaseMapper mapper;

    @Override
    public Optional<ReadNotification> getUserIdAndNotificationId(Integer userId, Integer notificationId) {
        return readNotificationJpaRepository.findOne((root, query, cb) -> {
            // Force Hibernate to fetch these immediately
            root.fetch("user", JoinType.LEFT);
            root.fetch("notification", JoinType.LEFT);

            return cb.and(
                    cb.isNull(root.get("deletedAt")),
                    cb.equal(root.get("user").get("id"), userId),
                    cb.equal(root.get("notification").get("id"), notificationId)
            );
        }).map(mapper::from);
    }

    @Override
    public ReadNotification save(ReadNotification readNotification) {
        ReadNotificationEntity entity = mapper.from(readNotification);
        readNotificationJpaRepository.save(entity);
        return mapper.from(entity);
    }
}
