package co.istab.blooddonationservice.presentation.blood.notification.model.response;

import lombok.Data;

@Data
public class OneSignalNotificationResponse {

    private String id;
    private Integer recipients;
}
