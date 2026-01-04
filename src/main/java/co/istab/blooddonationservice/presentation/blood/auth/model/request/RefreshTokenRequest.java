package co.istab.blooddonationservice.presentation.blood.auth.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshTokenRequest {

    @NotBlank(message = "token is required")
    private String token;
}
