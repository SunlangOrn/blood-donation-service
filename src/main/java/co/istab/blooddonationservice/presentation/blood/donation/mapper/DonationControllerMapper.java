package co.istab.blooddonationservice.presentation.blood.donation.mapper;

import co.istab.blooddonationservice.domain.blood.donation.entity.Donation;
import co.istab.blooddonationservice.presentation.blood.donation.model.request.CreateDonationRequest;
import co.istab.blooddonationservice.presentation.blood.donation.model.request.UpdateDonationRequest;
import co.istab.blooddonationservice.presentation.blood.report.DonationReportResponse;
import co.istab.blooddonationservice.presentation.blood.donation.model.response.DonationResponse;
import co.istab.blooddonationservice.presentation.blood.donation.model.response.DonationDetailResponse;
import org.mapstruct.*;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface DonationControllerMapper {

    @Mapping(target = "quantity", source = "quantity")
    DonationResponse from(Donation donation);

    @Mapping(target = "quantity", source = "quantity")
    DonationDetailResponse toDonationDetailResponse(Donation donation);

    Donation from(UpdateDonationRequest updateDonationRequest);

    Donation from(CreateDonationRequest donationRequest);

    DonationReportResponse fromList(Donation donation);

}
