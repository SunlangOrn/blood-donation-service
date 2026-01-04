package co.istab.blooddonationservice.infrastructure.database.mysql.repository;

import co.istab.blooddonationservice.infrastructure.database.mysql.Entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CategoryJpaRepository extends JpaRepository<CategoryEntity,Integer>, JpaSpecificationExecutor<CategoryEntity> {
}
