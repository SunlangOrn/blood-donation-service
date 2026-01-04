package co.istab.blooddonationservice.presentation.blood.password_reset.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ForgetPasswordRequest {

    @NotBlank(message ="PhoneNumber is required")
    private String phoneNumber;

}
