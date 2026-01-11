package co.istab.blooddonationservice.infrastructure.database.elasticsearch;

import co.istab.blooddonationservice.infrastructure.database.mysql.Entity.DonationEntity;
import co.istab.blooddonationservice.infrastructure.database.mysql.repository.DonationJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.DeleteQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class DonationElasticsearchService {

    private final ElasticsearchOperations operations;
    private final DonationJpaRepository jpaRepository;

    @Value("${spring.elasticsearch.indices.website-donation}")
    private String DONATION_INDEX;

    public void save(Integer donationId) {
        Optional<DonationEntity> optional = jpaRepository.findOne(((root, query, cb) ->
                cb.and(
                        cb.isNull(root.get("deletedAt")),
                        cb.equal(root.get("id"), donationId))));

        if(optional.isPresent()) {
            DonationEntity entity = optional.get();
            DonationDocument document = DonationDocument.of(
                    String.valueOf(entity.getId()),
                    entity.getId(),
                    entity.getName(),
                    entity.getLocation(),
                    entity.getPhoneNumber(),
                    entity.getQuantity(),
                    entity.getTypeBlood(),
                    entity.getStatus(),
                    entity.getCreatedAt());
            operations.save(document, IndexCoordinates.of(DONATION_INDEX));
        }
    }

    public void delete(Integer donationId){
        operations.delete(String.valueOf(donationId), IndexCoordinates.of(DONATION_INDEX));
    }

    public void saveAll(List<DonationDocument> documents){
        try {
            IndexCoordinates indexCoordinates = IndexCoordinates.of(DONATION_INDEX);

            // Delete and recreate index if it exists
            if(operations.indexOps(indexCoordinates).exists()){
                operations.indexOps(indexCoordinates).delete();
                log.info("Deleted existing index: {}", DONATION_INDEX);
            }

            operations.indexOps(indexCoordinates).create();
            log.info("Created index: {}", DONATION_INDEX);

            // Save all documents onto index
            if(documents != null && !documents.isEmpty()) {
                operations.save(documents, indexCoordinates);
                log.info("Saved {} documents into index {}", documents.size(), DONATION_INDEX);
            } else {
                log.warn("No documents to save into index: {}", DONATION_INDEX);
            }

        } catch (Exception e) {
            log.error("Failed to sync documents to index {}: {}", DONATION_INDEX, e.getMessage(), e);
        }
    }

}
