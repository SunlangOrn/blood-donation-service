package co.istab.blooddonationservice.presentation.blood.password_reset.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PasswordResetRequest {

    @NotBlank(message = "phoneNumber is required")
    private String phoneNumber;

    @NotBlank(message = "Otp is required")
    private String otpCode;

    @NotBlank(message = "password is required")
    private String newPassword;

    @NotBlank(message = "confirmPassword is required")
    private String confirmPassword;
}
