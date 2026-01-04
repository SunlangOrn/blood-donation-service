package co.istab.blooddonationservice.infrastructure.database.mysql.repository;

import co.istab.blooddonationservice.infrastructure.database.mysql.Entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface RoelJpaRepository extends JpaRepository<RoleEntity, Integer> , JpaSpecificationExecutor<RoleEntity> {
}
