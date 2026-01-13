package co.istab.blooddonationservice.infrastructure.database.mapper;

import co.istab.blooddonationservice.domain.blood.donation_action.entity.DonationAction;
import co.istab.blooddonationservice.infrastructure.database.elasticsearch.DonationActionDocument;
import co.istab.blooddonationservice.infrastructure.database.mysql.Entity.DonationActionEntity;

import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface DonationActionDatabaseMapper {

    @Mapping(source = "donationId", target = "donation.id")
    @Mapping(source = "userId", target = "user.id")
    DonationActionEntity from(DonationAction donationAction);

    @Mapping(source = "donation.id", target = "donationId")
    @Mapping(target = "donation", source = "donation")
    @Mapping(source = "user.id", target = "userId")
    DonationAction from(DonationActionEntity donationActionEntity);


    @Mapping(target = "actionId", source = "id")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "isConfirmed", source = "isConfirmed")
    @Mapping(target = "donationId", source = "donation.id")
    @Mapping(target = "userId", source = "user.id")
    DonationActionDocument mapElastic(DonationActionEntity entity);
}
