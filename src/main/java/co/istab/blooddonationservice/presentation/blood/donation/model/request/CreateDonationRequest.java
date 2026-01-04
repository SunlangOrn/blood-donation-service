package co.istab.blooddonationservice.presentation.blood.donation.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateDonationRequest {

    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must be less than 255 characters")
    private String name;

    @NotBlank(message = "Location is required")
    @Size(max = 255, message = "Location must be less than 255 characters")
    private String location;

    @NotNull(message = "Location is required")
    private Integer quantity;

    @NotBlank(message = "Blood type is required")
    @Pattern(regexp = "^(A|B|AB|O)[+-]$", message = "Invalid blood type format (e.g., A+, O-)")
    private String typeBlood;

    @NotBlank(message ="PhoneNumber is required")
    @Size(max = 10, message ="PhoneNumber is must be less that 10 digit" )
    private String phoneNumber;

    @Size(max = 255, message = "Note must be less than 255 characters")
    private String note;
}
