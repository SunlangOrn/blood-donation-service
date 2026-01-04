package co.istab.blooddonationservice.presentation.blood.notification.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class OneSignalNotificationRequest {

    @JsonProperty("app_id")
    private String appId;

    @JsonProperty("include_external_user_ids")
    private List<String> includeExternalUserIds;

    private Map<String, String> headings;

    private Map<String, String> contents;

    private Map<String, Object> data;
}
