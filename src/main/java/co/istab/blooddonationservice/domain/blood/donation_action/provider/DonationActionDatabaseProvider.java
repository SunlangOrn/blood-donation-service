package co.istab.blooddonationservice.domain.blood.donation_action.provider;

import co.istab.blooddonationservice.domain.blood.donation_action.constant.DonationActionStatus;
import co.istab.blooddonationservice.domain.blood.donation_action.entity.DonationAction;

import java.util.List;
import java.util.Optional;

public interface DonationActionDatabaseProvider {

    Optional<DonationAction> getById(Integer id);

    Optional<DonationAction> getActionById(Integer actionId);

    DonationAction save(DonationAction donationAction);

    List<DonationAction> findByDonation_IdAndUser_Id(Integer donationId, Integer userId);

    List<DonationAction> getByDonationId(Integer donationId);

    List<DonationAction> getByUserId(Integer userId);

}
