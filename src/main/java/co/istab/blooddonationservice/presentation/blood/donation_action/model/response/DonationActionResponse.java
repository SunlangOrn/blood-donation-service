package co.istab.blooddonationservice.presentation.blood.donation_action.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DonationActionResponse {

    private Integer actionId;

    private Integer donationId;

    private Integer userId;

    private String status;

    private Boolean isConfirmed;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifiedAt;
}