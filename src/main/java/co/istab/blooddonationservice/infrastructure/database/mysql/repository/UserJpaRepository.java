package co.istab.blooddonationservice.infrastructure.database.mysql.repository;

import co.istab.blooddonationservice.infrastructure.database.mysql.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface UserJpaRepository extends JpaRepository<UserEntity, Integer> , JpaSpecificationExecutor<UserEntity> {
}
