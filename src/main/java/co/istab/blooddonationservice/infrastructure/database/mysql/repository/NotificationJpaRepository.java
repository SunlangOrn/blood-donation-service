package co.istab.blooddonationservice.infrastructure.database.mysql.repository;

import co.istab.blooddonationservice.infrastructure.database.mysql.Entity.NotificationEntity;
import co.istab.blooddonationservice.share.entity.PaginationQuery;
import co.istab.blooddonationservice.share.entity.Paging;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface NotificationJpaRepository extends JpaRepository<NotificationEntity,Integer>, JpaSpecificationExecutor<NotificationEntity> {
    @Query("""
    SELECT n FROM NotificationEntity n
    JOIN FETCH n.user u
    WHERE n.user.id = :userId
      AND n.id NOT IN (
          SELECT rn.notification.id FROM ReadNotificationEntity rn WHERE rn.user.id = :userId
      )
      AND n.deletedAt IS NULL
    ORDER BY n.createdAt DESC
""")
    Page<NotificationEntity> getUnread(@Param("userId") Integer userId, Pageable pageable);
}

