package co.istab.blooddonationservice.domain.blood.notification.application;

import co.istab.blooddonationservice.domain.blood.notification.service.OneSignalService;
import co.istab.blooddonationservice.domain.blood.user.entity.User;
import co.istab.blooddonationservice.presentation.blood.notification.model.request.OneSignalNotificationRequest;
import co.istab.blooddonationservice.presentation.blood.notification.model.response.OneSignalNotificationResponse;
import co.istab.blooddonationservice.share.entity.Metadata;
import com.mysql.cj.x.protobuf.MysqlxExpr;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Meta;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class OneSignalServiceFacade implements OneSignalService {

    private final WebClient webClient;

    @Value("${onesignal.app-id}")
    private String appId;

    @Override
    public void sendNotification(
            String externalUserId,
            String title,
            String message,
            Map<String,Object> data)
    {

        Map<String,String> heading = new HashMap<>();
        heading.put("title", title);

        Map<String,String> content = new HashMap<>();
        content.put("message", message);

        OneSignalNotificationRequest request =OneSignalNotificationRequest.builder()
                .appId(appId)
                .includeExternalUserIds(List.of(externalUserId))
                .headings(heading)
                .contents(content)
                .data(data != null ? data : new HashMap<>() )
                .build();

        webClient.post()
                .uri("/notifications")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(OneSignalNotificationResponse.class)
                .subscribe(
                        response -> log.info("Push notification sent to user {}: {} recipients",
                                externalUserId, response.getRecipients()),
                        error -> log.error("Error sending push notification to user {}: {}",
                                externalUserId, error.getMessage())
                );
    }
}
