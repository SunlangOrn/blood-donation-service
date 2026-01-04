package co.istab.blooddonationservice.domain.blood.donation.provider;

import co.istab.blooddonationservice.domain.blood.donation.entity.Donation;
import co.istab.blooddonationservice.share.entity.PaginationQuery;
import co.istab.blooddonationservice.share.entity.Paging;

import java.util.List;
import java.util.Optional;

public interface DonationDatabaseProvider {

    Paging<Donation> list(PaginationQuery query);

    Optional<Donation> getById(Integer id);

    Optional<Donation> getByName(String name);

    Donation save(Donation donation);

    List<Donation> getAllByDonorId(Integer donorId);

}
