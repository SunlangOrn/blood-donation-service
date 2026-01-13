package co.istab.blooddonationservice.domain.blood.donation_action.application;

import co.istab.blooddonationservice.domain.blood.donation.constant.DonationStatus;
import co.istab.blooddonationservice.domain.blood.donation.entity.Donation;
import co.istab.blooddonationservice.domain.blood.donation.exception.DonationException;
import co.istab.blooddonationservice.domain.blood.donation.provider.DonationDatabaseProvider;
import co.istab.blooddonationservice.domain.blood.donation_action.constant.DonationActionStatus;
import co.istab.blooddonationservice.domain.blood.donation_action.entity.DonationAction;
import co.istab.blooddonationservice.domain.blood.donation_action.exception.DonationActionException;
import co.istab.blooddonationservice.domain.blood.donation_action.handler.ActionElasticsearchSync;
import co.istab.blooddonationservice.domain.blood.donation_action.provider.DonationActionDatabaseProvider;
import co.istab.blooddonationservice.domain.blood.donation_action.service.DonationActionService;
import co.istab.blooddonationservice.domain.blood.notification.service.NotificationService;
import co.istab.blooddonationservice.domain.blood.user.entity.User;
import co.istab.blooddonationservice.domain.blood.user.exception.UserException;
import co.istab.blooddonationservice.domain.blood.user.provider.UserDatabaseProvider;
import co.istab.blooddonationservice.share.entity.Metadata;
import co.istab.blooddonationservice.share.handler.metadata.MetadataHandler;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DonationActionServiceFacade implements DonationActionService {


    private final DonationDatabaseProvider donationProvider;
    private final DonationActionDatabaseProvider donationActionProvider;
    private final UserDatabaseProvider userProvider;
    private final NotificationService  notificationService;

    @Transactional
    @Override
    public void syncMatchAll(){
        donationActionProvider.sync();
    }

    @MetadataHandler
    @Override
    public DonationAction getActionId(Metadata metadata, Integer actionId) {
        Integer userId = Integer.parseInt(metadata.getUserId());

        DonationAction action = donationActionProvider.getActionById(actionId)
                .orElseThrow(DonationActionException::notFound);

        if (!userId.equals(action.getUserId())) {
            throw DonationActionException.cannotAccess();
        }

        Optional.ofNullable(action.getDonation()).ifPresent(d -> d.getId());

        return action;
    }


    @Transactional
    @ActionElasticsearchSync
    @MetadataHandler
    @Override
    public DonationAction donate(Metadata metadata , Integer donationId) {

        Donation donation = donationProvider.getById(donationId)
                .orElseThrow(DonationException::notFound);

        validateCanDonation(donation, Integer.parseInt(metadata.getUserId()));
        checkDuplicateOrRejected(donationId, Integer.parseInt(metadata.getUserId()));

        DonationAction action = new DonationAction();
        action.setDonationId(donationId);
        action.setUserId(Integer.parseInt(metadata.getUserId()));
        action.setStatus(DonationActionStatus.PENDING);
        action.setIsConfirmed(false);
        action.setCreatedAt(new Date());

        action.setQuantity(donation.getQuantity());

        DonationAction savedAction = donationActionProvider.save(action);

        User donor = userProvider.getUserById(Integer.parseInt(metadata.getUserId()))
                .orElseThrow(UserException::notFound);

        String title = "New Donation Offer";
        String donorName = donor.getFirstName() + " " + donor.getLastName();
        String message = donorName + " wants to donate " +
                donation.getQuantity() + " unit(s) of " +
                donation.getTypeBlood() + " blood";


        notificationService.createAndSendNotification(
                donation.getDonorId(),
                title,
                message,
                "DONATION_OFFER_RECEIVED",
                donation.getId(),
                action.getId()
        );

        return savedAction;
    }

    @ActionElasticsearchSync
    @Transactional
    @MetadataHandler
    @Override
    public DonationAction cancel(Metadata metadata, Integer actionId) {

        DonationAction action = donationActionProvider.getById(actionId)
                .orElseThrow(DonationActionException::notFound);

        Integer userId = Integer.parseInt(metadata.getUserId());
        if(!Objects.equals(action.getUserId(), userId)){
            throw DonationActionException.notAllow();
        }

        action.setStatus(DonationActionStatus.CANCELLED);
        action.setModifiedAt(new Date());

        DonationAction cancelled  = donationActionProvider.save(action);

        Donation donation = donationProvider.getById(action.getDonationId())
                .orElseThrow(DonationException::notFound);

        User donor = userProvider.getUserById(Integer.parseInt(metadata.getUserId()))
                .orElseThrow(UserException::notFound);

        String title = "Your donation have cancelled";
        String donorName = donor.getFirstName() + " " + donor.getLastName();
        String message = donorName + " has cancelled their donation ";

        notificationService.createAndSendNotification(
                donation.getDonorId(),
                title,
                message,
                "DONATION_OFFER_CANCELLED",
                donation.getId(),
                cancelled.getId()
        );
        return cancelled;
    }

    @ActionElasticsearchSync
    @Transactional
    @MetadataHandler
    @Override
    public DonationAction accept(Metadata metadata,Integer actionId) {

        DonationAction action = donationActionProvider.getById(actionId)
                .orElseThrow(DonationException::notFound);

        Donation donation = donationProvider.getById(action.getDonationId())
                .orElseThrow(DonationException::notFound);

        Integer userId = Integer.parseInt(metadata.getUserId());
        if (!Objects.equals(donation.getDonorId(), userId)) {
           throw DonationActionException.acceptFail();
        }

        action.setIsConfirmed(true);
        action.setStatus(DonationActionStatus.DONATED);
        action.setModifiedAt(new Date());
        
        DonationAction accepted = donationActionProvider.save(action);

        donation.setStatus(DonationStatus.COMPLETED);
        donation.setModifiedAt(new Date());
        donationProvider.save(donation);

        List<DonationAction> allActions = donationActionProvider.getByDonationId(donation.getId());
        for (DonationAction otherAction : allActions) {
            if (!otherAction.getId().equals(actionId) &&
                    DonationActionStatus.PENDING.equals(otherAction.getStatus())) {

                otherAction.setStatus(DonationActionStatus.REJECTED);
                donationActionProvider.save(otherAction);

            }
        }

        User donor = userProvider.getUserById(Integer.valueOf(metadata.getUserId()))
                .orElseThrow(UserException::notFound);


        String title = "Donation Offer Accepted";
        String requesterName = donor.getFirstName() + " " + donor.getLastName();
        String message = requesterName + " has accepted your donation offer for " +
                donation.getQuantity() + " unit(s) of " +
                donation.getTypeBlood() + " blood";

        notificationService.createAndSendNotification(
                action.getUserId(),
                title,
                message,
                "DONATION_OFFER_ACCEPTED",
                donation.getId(),
                accepted.getId()
        );
        return accepted;
    }

    @ActionElasticsearchSync
    @Transactional
    @MetadataHandler
    @Override
    public DonationAction reject(Metadata metadata ,Integer actionId) {

        DonationAction action = donationActionProvider.getById(actionId)
                .orElseThrow(DonationActionException::notFound);

        Donation donate = donationProvider.getById(action.getDonationId())
                .orElseThrow(DonationException::notFound);

        Integer userId = Integer.parseInt(metadata.getUserId());
        if (!Objects.equals(donate.getDonorId(), userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Only the blood requester can reject donation offers");
        }

        action.setStatus(DonationActionStatus.REJECTED);
        action.setIsConfirmed(false);
        action.setModifiedAt(new Date());

        DonationAction rejected = donationActionProvider.save(action);

        User requester = userProvider.getUserById(Integer.parseInt(metadata.getUserId()))
                .orElseThrow(UserException::notFound);

        String title = "Donation Have Rejected";
        String requesterName = requester.getFirstName() + " " + requester.getLastName();
        String message = requesterName + " has declined your donation offer";

        notificationService.createAndSendNotification(
                action.getUserId(),
                title,
                message,
                "DONATION_OFFER_REJECTED",
                donate.getId(),
                rejected.getId()
        );

        return rejected;
    }

    @Transactional
    @MetadataHandler
    @Override
    public List<DonationAction> getUserId(Metadata metadata) {
        return donationActionProvider.getByUserId(Integer.parseInt(metadata.getUserId()))
                .stream().toList();
    }

    private void checkDuplicateOrRejected(Integer donationId,Integer userId) {
        List<DonationAction> entity = donationActionProvider
                .findByDonation_IdAndUser_Id(donationId, userId);

        for (DonationAction action : entity) {
            if (DonationActionStatus.REJECTED.equals(action.getStatus())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                        "You were rejected from this donation");
            }
            if (DonationActionStatus.DONATED.equals(action.getStatus())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "You already have pending offer");
            }
        }
    }

    private void validateCanDonation(Donation donation , Integer userId) {

         if (donation.getDonorId().equals(userId)) {
             throw new ResponseStatusException(
                     HttpStatus.BAD_REQUEST,
                     "You cannot donate your own donation request");
         }

        if (DonationStatus.COMPLETED.equals(donation.getStatus())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,"Donation is already completed");
        }
    }
}
