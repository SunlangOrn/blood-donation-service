package co.istab.blooddonationservice.infrastructure.database.provider;

import co.istab.blooddonationservice.domain.blood.donation.entity.Donation;
import co.istab.blooddonationservice.domain.blood.donation.provider.DonationDatabaseProvider;
import co.istab.blooddonationservice.infrastructure.database.elasticsearch.DonationDocument;
import co.istab.blooddonationservice.infrastructure.database.elasticsearch.DonationElasticsearchService;
import co.istab.blooddonationservice.infrastructure.database.mapper.DonationDatabaseMapper;
import co.istab.blooddonationservice.infrastructure.database.mysql.Entity.DonationEntity;
import co.istab.blooddonationservice.infrastructure.database.mysql.repository.DonationJpaRepository;
import co.istab.blooddonationservice.share.entity.PaginationQuery;
import co.istab.blooddonationservice.share.entity.Paging;
import co.istab.blooddonationservice.share.utility.PageNumberUtility;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DonationDatabaseProviderFacade implements DonationDatabaseProvider {

    private final DonationJpaRepository repository;
    private final DonationElasticsearchService donationElasticsearchService;
    private final DonationDatabaseMapper mapper;

    @Override
    @Transactional
    public void sync() {
        List<DonationDocument> donationDocuments = repository
                .findAll()
                .stream()
                .filter(d -> d.getDeletedAt() == null)
                .map(mapper::mapElastic)
                .toList();
        donationElasticsearchService.saveAll(donationDocuments);
    }

    @Override
    public Paging<Donation> list(PaginationQuery query) {
        Page<DonationEntity> entityPage =
                repository.findAll(
                        (root, querySpec, criteriaBuilder) -> {
                            List<Predicate> predicates = new ArrayList<>();
                            predicates.add(criteriaBuilder.isNull(root.get("deletedAt")));
                            if (query.getKeyword() != null && !query.getKeyword().trim().isEmpty()) {
                                predicates.add(criteriaBuilder.like(root.get("name"), "%" + query.getKeyword() + "%"));
                            }
                            if (query.getTypeBlood() != null && !query.getTypeBlood().trim().isEmpty()) {
                                predicates.add(criteriaBuilder.equal(root.get("typeBlood"), query.getTypeBlood()));
                            }

                            // Status filter
                            if (query.getStatus() != null && !query.getStatus().trim().isEmpty()) {
                                predicates.add(criteriaBuilder.equal(root.get("status"), query.getStatus()));
                            }

                            // Location filter
                            if (query.getLocation() != null && !query.getLocation().trim().isEmpty()) {
                                predicates.add(criteriaBuilder.like(root.get("location"), "%" + query.getLocation() + "%"));
                            }
                            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
                        },
                        PageRequest.of(
                                PageNumberUtility.in(query.getPage()),
                                query.getSize(),
                                Sort.by(Sort.Direction.DESC, "id")));
        return Paging.<Donation>builder()
                .items(entityPage.stream().map(mapper::form).toList())
                .page(PageNumberUtility.out(entityPage.getNumber()))
                .size(entityPage.getSize())
                .totalPages(entityPage.getTotalPages())
                .total(entityPage.getNumberOfElements())
                .build();
    }


    @Override
    public Optional<Donation> getById(Integer id) {
        return repository
                .findOne((root, query, criteriaBuilder) ->
                        criteriaBuilder.and(
                                criteriaBuilder.isNull(root.get("deletedAt")),
                                criteriaBuilder.equal(root.get("id"), id)))
                .map(mapper::form);
    }

    @Override
    public Optional<Donation> getByName(String name) {
        return repository
                .findOne((root, query, criteriaBuilder) ->
                        criteriaBuilder.and(
                                criteriaBuilder.isNull(root.get("deletedAt")),
                                criteriaBuilder.equal(root.get("name"), name)))
                .map(mapper::form);
    }

    @Override
    public Donation save(Donation donation) {
        DonationEntity donationEntity = mapper.form(donation);
        repository.save(donationEntity);
        return mapper.form(donationEntity);
    }

    @Override
    public List<Donation> getAllByDonorId(Integer donorId) {
        return repository.findAll((root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.isNull(root.get("deletedAt")),
                        criteriaBuilder.equal(root.get("donor").get("id"), donorId))
        ).stream().map(mapper::form).toList();
    }

}
