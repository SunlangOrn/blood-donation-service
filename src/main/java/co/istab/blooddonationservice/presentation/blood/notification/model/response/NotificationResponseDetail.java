package co.istab.blooddonationservice.presentation.blood.notification.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationResponseDetail {

    private Integer id;
    private String title;
    private String message;
    private String type;
    private Boolean isRead;
    private Date readAt;

    private Integer referenceActionId;
    private Integer referencePostId;
    private Integer referenceDonationId;

    private Date createdAt;
}
