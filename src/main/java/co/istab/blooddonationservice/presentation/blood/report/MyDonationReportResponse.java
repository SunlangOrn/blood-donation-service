package co.istab.blooddonationservice.presentation.blood.report;

import co.istab.blooddonationservice.domain.blood.donation_action.constant.DonationActionStatus;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class MyDonationReportResponse {

    private Integer id;
    private String name;
    private String phoneNumber;
    private String location;
    private String typeBlood;
    private DonationActionStatus status;
    private Date createdAt;
}
