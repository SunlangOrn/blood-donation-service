package co.istab.blooddonationservice.domain.blood.donation_action.entity;

import co.istab.blooddonationservice.domain.blood.donation.entity.Donation;
import co.istab.blooddonationservice.domain.blood.donation_action.constant.DonationActionStatus;
import lombok.Data;

import java.util.Date;

@Data
public class DonationAction {

    private Integer id;
    private Integer donationId;
    private Integer userId;
    private Integer quantity;
    private Donation donation;
    private DonationActionStatus status;
    private Boolean isConfirmed;
    private Date createdAt;
    private Date modifiedAt;
    private Date deletedAt;
}
