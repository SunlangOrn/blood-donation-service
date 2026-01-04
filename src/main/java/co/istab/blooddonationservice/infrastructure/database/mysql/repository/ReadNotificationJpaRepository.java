package co.istab.blooddonationservice.infrastructure.database.mysql.repository;

import co.istab.blooddonationservice.infrastructure.database.mysql.Entity.ReadNotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReadNotificationJpaRepository extends JpaRepository<ReadNotificationEntity, Integer>, JpaSpecificationExecutor<ReadNotificationEntity> {

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM ReadNotificationEntity r WHERE r.id = :id AND r.user.id = :userId")
    boolean existsReadNotificationAndUserId(@Param("id") Integer id, @Param("userId") Integer userId);

    Optional<ReadNotificationEntity> findByIdAndUser_Id(Integer id,Integer userId);

}
