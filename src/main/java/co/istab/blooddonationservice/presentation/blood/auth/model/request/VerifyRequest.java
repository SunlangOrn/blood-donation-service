package co.istab.blooddonationservice.presentation.blood.auth.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VerifyRequest {

    @NotBlank(message = "phoneNumber is required")
    private String phoneNumber;

    @NotBlank(message = "verification code is required")
    private String verificationCode;

}
