package co.istab.blooddonationservice.presentation.blood.donation.model.response;

import co.istab.blooddonationservice.domain.blood.donation.constant.DonationStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DonationDetailResponse {

    private Integer id;
    private String name;
    private String location;
    private String phoneNumber;
    private String typeBlood;
    private Integer quantity;
    private String note;
}
