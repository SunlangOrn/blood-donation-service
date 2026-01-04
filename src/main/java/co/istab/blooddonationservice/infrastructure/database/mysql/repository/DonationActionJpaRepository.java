package co.istab.blooddonationservice.infrastructure.database.mysql.repository;

import co.istab.blooddonationservice.infrastructure.database.mysql.Entity.DonationActionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DonationActionJpaRepository extends JpaRepository<DonationActionEntity,Integer> , JpaSpecificationExecutor<DonationActionEntity> {
}
