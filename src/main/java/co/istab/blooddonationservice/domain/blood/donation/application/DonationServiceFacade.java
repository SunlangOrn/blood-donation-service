package co.istab.blooddonationservice.domain.blood.donation.application;

import co.istab.blooddonationservice.domain.blood.donation.constant.DonationStatus;
import co.istab.blooddonationservice.domain.blood.donation.entity.Donation;
import co.istab.blooddonationservice.domain.blood.donation.exception.DonationException;
import co.istab.blooddonationservice.domain.blood.donation.handler.DonationElasticsearchSync;
import co.istab.blooddonationservice.domain.blood.donation.provider.DonationDatabaseProvider;
import co.istab.blooddonationservice.domain.blood.donation.service.DonationService;
import co.istab.blooddonationservice.domain.blood.user.entity.User;
import co.istab.blooddonationservice.domain.blood.user.provider.UserDatabaseProvider;
import co.istab.blooddonationservice.share.entity.Metadata;
import co.istab.blooddonationservice.share.entity.PaginationQuery;
import co.istab.blooddonationservice.share.entity.Paging;
import co.istab.blooddonationservice.share.handler.metadata.MetadataHandler;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Slf4j
@Service
@RequiredArgsConstructor
public class DonationServiceFacade implements DonationService {

    private final DonationDatabaseProvider donationProvider;
    private final UserDatabaseProvider userProvider;

    @Override
    public void syncAll() {
        donationProvider.sync();
    }

    @MetadataHandler
    @Transactional
    @Override
    public List<Donation> myRequests(Metadata metadata){
        Integer donorId = Integer.parseInt(metadata.getUserId());
        return donationProvider.getAllByDonorId(donorId);
    }

    @MetadataHandler
    @Transactional
    @Override
    public Paging<Donation> list(Metadata metadata, PaginationQuery query) {
        Paging<Donation> donationPaging = donationProvider.list(query);
        if(donationPaging.getItems().isEmpty()) return donationPaging;
        return donationProvider.list(query);
    }

    @MetadataHandler
    @Transactional
    @Override
    public Donation view(Metadata metadata, Integer id) {

        return donationProvider.getById(id).orElseThrow(DonationException::notFound);
    }

    @DonationElasticsearchSync
    @MetadataHandler
    @Transactional
    @Override
    public Donation create( Metadata metadata ,Donation donation) {

        User donor =userProvider.getUserById(Integer.parseInt(metadata.getUserId()))
                        .orElseThrow(DonationException::notFound);

        donation.setDonorId(donor.getId());
        donation.setTimeExpired(LocalDateTime.now().plusHours(40));
        donation.setStatus(DonationStatus.PENDING);
        donation.setCreatedAt(new Date());

        return donationProvider.save(donation);
    }

    @DonationElasticsearchSync
    @MetadataHandler
    @Transactional
    @Override
    public Donation update( Metadata metadata ,Integer donationId, Donation donation) {

        Integer userId = Integer.parseInt(metadata.getUserId());

        Donation oldEntity = donationProvider.getById(donationId)
                .orElseThrow(DonationException::notFound);

        if (!Objects.equals(oldEntity.getDonorId(), userId)) {
            throw DonationException.notOwnDonation();
        }

         if(DonationStatus.COMPLETED.equals(donation.getStatus())) {
             throw DonationException.alreadyDonated();
         }
         oldEntity.setName(donation.getName());
         oldEntity.setPhoneNumber(donation.getPhoneNumber());
         oldEntity.setLocation(donation.getLocation());
         oldEntity.setQuantity(donation.getQuantity());
         oldEntity.setTypeBlood(donation.getTypeBlood());
         oldEntity.setNote(donation.getNote());
         oldEntity.setModifiedAt(new Date());

        return donationProvider.save(oldEntity);
    }

    @DonationElasticsearchSync
    @MetadataHandler
    @Override
    public Donation delete(Metadata metadata, Integer id) {


        Donation donation = donationProvider.getById(id)
                .orElseThrow(DonationException::notFound);

        Integer userId = Integer.parseInt(metadata.getUserId());

        if (!donation.getDonorId().equals(userId)) {
            throw  DonationException.cannotDelete();
        }

        if(DonationStatus.COMPLETED.equals(donation.getStatus())) {
            throw DonationException.cannotDeleteCompleted();
        }
        donation.setDeletedAt(new Date());

        return donationProvider.save(donation);
    }

}
