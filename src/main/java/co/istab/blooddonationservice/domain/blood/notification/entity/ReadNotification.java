package co.istab.blooddonationservice.domain.blood.notification.entity;

import co.istab.blooddonationservice.domain.blood.user.entity.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Builder
public class ReadNotification {
    private Integer id;
    private Date readAt;
    private Integer userId;
    private Integer notificationId;
    private Date createdAt;
    private Date modifiedAt;
    private Date deletedAt;
}
