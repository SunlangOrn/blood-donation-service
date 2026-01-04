package co.istab.blooddonationservice.presentation.blood.donation_action.mapper;

import co.istab.blooddonationservice.domain.blood.donation_action.entity.DonationAction;
import co.istab.blooddonationservice.presentation.blood.donation_action.model.response.DonationActionResponse;
import co.istab.blooddonationservice.presentation.blood.report.MyDonationReportResponse;
import org.mapstruct.*;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface DonationActionControllerMapper {

    @Mapping(source = "donationAction.id", target = "actionId")
    @Mapping(source = "donationId", target = "donationId")
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "isConfirmed", target = "isConfirmed")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "modifiedAt", target = "modifiedAt")
    DonationActionResponse from(DonationAction donationAction);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "name", source = "donation.name")
    @Mapping(target = "phoneNumber", source = "donation.phoneNumber")
    @Mapping(target = "location", source = "donation.location")
    @Mapping(target = "typeBlood", source = "donation.typeBlood")
    MyDonationReportResponse fromList(DonationAction donationAction);
}
