package co.istab.blooddonationservice.domain.blood.donation_action.service;

import co.istab.blooddonationservice.domain.blood.donation.entity.Donation;
import co.istab.blooddonationservice.domain.blood.donation_action.entity.DonationAction;
import co.istab.blooddonationservice.share.entity.Metadata;
import org.springframework.data.jpa.repository.Meta;

import java.util.List;

public interface DonationActionService {

    DonationAction donate (Metadata metadata, Integer donationId);

    DonationAction cancel (Metadata metadata,Integer actionId);

    DonationAction accept (Metadata metadata,Integer actionId);

    DonationAction reject (Metadata metadata,Integer actionId);

    List<DonationAction> getUserId (Metadata metadata);

    DonationAction getActionId (Metadata metadata, Integer actionId);
}

