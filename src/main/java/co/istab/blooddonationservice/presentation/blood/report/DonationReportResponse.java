package co.istab.blooddonationservice.presentation.blood.report;

import co.istab.blooddonationservice.domain.blood.donation.constant.DonationStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class DonationReportResponse {

    private Integer id;
    private String name;
    private String location;
    private String typeBlood;
    private Integer quantity;
    @Enumerated(EnumType.STRING)
    private DonationStatus status;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date createdAt;
}
