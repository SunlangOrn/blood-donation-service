package co.istab.blooddonationservice.infrastructure.Config.Notification;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class OneSignalConfig {

    @Value("${onesignal.api-url}")
    private String apiUrl;

    @Value("${onesignal.api-key}")
    private String restApiKey;

    @Bean
    public WebClient oneSignalWebClient() {
        return WebClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader("Authorization", "Basic " + restApiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}
