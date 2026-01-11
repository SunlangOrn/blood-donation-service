package co.istab.blooddonationservice.domain.blood.donation.service;

import co.istab.blooddonationservice.domain.blood.donation.entity.Donation;
import co.istab.blooddonationservice.domain.blood.user.entity.User;
import co.istab.blooddonationservice.share.entity.Metadata;
import co.istab.blooddonationservice.share.entity.PaginationQuery;
import co.istab.blooddonationservice.share.entity.Paging;
import org.springframework.data.jpa.repository.Meta;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DonationService {

    Paging<Donation> list(Metadata metadata,PaginationQuery query);

    Donation view(Metadata metadata, Integer id);

    Donation create(Metadata metadata, Donation donation);

    Donation update(Metadata metadata, Integer donationId ,Donation donation);

    Donation delete(Metadata metadata ,Integer id);

    List<Donation> myRequests(Metadata metadata);

    void syncAll();

}
