package co.istab.blooddonationservice.presentation.blood.donation.model.response;

import co.istab.blooddonationservice.domain.blood.donation.constant.DonationStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DonationResponse {

    private Integer id;
    private String name;
    private String location;
    private String phoneNumber;
    private String typeBlood;
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private DonationStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timeExpired;
    private Integer donorId;
}

