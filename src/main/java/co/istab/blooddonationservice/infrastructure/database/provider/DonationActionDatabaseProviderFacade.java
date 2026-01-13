package co.istab.blooddonationservice.infrastructure.database.provider;

import co.istab.blooddonationservice.domain.blood.donation_action.entity.DonationAction;
import co.istab.blooddonationservice.domain.blood.donation_action.provider.DonationActionDatabaseProvider;
import co.istab.blooddonationservice.infrastructure.database.elasticsearch.DonationActionDocument;
import co.istab.blooddonationservice.infrastructure.database.elasticsearch.DonationActionElasticsearchService;
import co.istab.blooddonationservice.infrastructure.database.elasticsearch.DonationDocument;
import co.istab.blooddonationservice.infrastructure.database.mapper.DonationActionDatabaseMapper;
import co.istab.blooddonationservice.infrastructure.database.mysql.Entity.DonationActionEntity;
import co.istab.blooddonationservice.infrastructure.database.mysql.repository.DonationActionJpaRepository;
import jakarta.persistence.criteria.JoinType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DonationActionDatabaseProviderFacade implements DonationActionDatabaseProvider {

    private final DonationActionJpaRepository repository;
    private final DonationActionDatabaseMapper mapper;
    private final DonationActionElasticsearchService donationActionElasticsearchService;

    @Override
    public void sync(){
        List<DonationActionDocument> documents = repository.findAll()
                .stream()
                .filter(d -> d.getDeletedAt() == null)
                .map(mapper::mapElastic)
                .toList();
        donationActionElasticsearchService.saveAll(documents);

    }

    @Override
    public List<DonationAction> getByUserId(Integer userId) {
        return repository
                .findAll((root, query, criteriaBuilder) ->
                        criteriaBuilder.and(
                                criteriaBuilder.isNull(root.get("deletedAt")),
                                criteriaBuilder.equal(root.get("user").get("id"), userId)
                        ),
                        Sort.by(Sort.Direction.DESC, "id")
                )
                .stream()
                .map(mapper::from)
                .toList();
    }

    @Override
    public List<DonationAction> getByDonationId(Integer donationId) {
        return repository
                .findAll((root, query, criteriaBuilder) ->
                        criteriaBuilder.and(
                                criteriaBuilder.isNull(root.get("deletedAt")),
                                criteriaBuilder.equal(root.get("donation").get("id"), donationId)
                        ),
                        Sort.by(Sort.Direction.DESC, "id")
                )
                .stream()
                .map(mapper::from)
                .toList();
    }

    @Override
    public List<DonationAction> findByDonation_IdAndUser_Id(Integer donationId, Integer userId) {
        return repository
                .findOne((root, query, criteriaBuilder) ->
                        criteriaBuilder.and(
                                criteriaBuilder.isNull(root.get("deletedAt")),
                                criteriaBuilder.equal(root.get("donation").get("id"), donationId),
                                criteriaBuilder.equal(root.get("user").get("id"), userId)
                        )

                )
                .stream()
                .map(mapper::from)
                .toList();
    }

    @Override
    public Optional<DonationAction> getById(Integer id) {
        return repository
                .findOne((root, query, criteriaBuilder) ->
                        criteriaBuilder.and(
                                criteriaBuilder.isNull(root.get("deletedAt")),
                                criteriaBuilder.equal(root.get("id"), id)))
                .map(mapper::from);
    }

    @Override
    public Optional<DonationAction> getActionById(Integer id) {
        return repository.findOne((root, query, cb) -> {
            root.fetch("donation"); // fetch donation eagerly
            return cb.and(
                    cb.isNull(root.get("deletedAt")),
                    cb.equal(root.get("id"), id)
            );
        }).map(mapper::from);
    }

    @Override
    public DonationAction save(DonationAction donationAction) {
        DonationActionEntity donationActionEntity = mapper.from(donationAction);
        repository.save(donationActionEntity);
        return mapper.from(donationActionEntity);
    }
}
