package co.istab.blooddonationservice.presentation.blood.notification.model.response;

import lombok.Data;

import java.util.Date;

@Data
public class NotificationResponse {
    private Integer notificationId;
    private String title;
    private String message;
    private Date createdAt;

}
