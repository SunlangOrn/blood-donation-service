package co.istab.blooddonationservice.presentation.blood.password_reset.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordResetResponse {

    private int status;
    private String message;
}
