package co.istab.blooddonationservice.domain.blood.notification.service;

import co.istab.blooddonationservice.share.entity.Metadata;
import org.springframework.data.jpa.repository.Meta;

import java.util.Map;

public interface OneSignalService {

    void sendNotification(String externalUserId,String title,
                          String message, Map<String,Object> data);
}
