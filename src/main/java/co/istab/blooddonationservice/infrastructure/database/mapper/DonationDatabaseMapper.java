package co.istab.blooddonationservice.infrastructure.database.mapper;

import co.istab.blooddonationservice.domain.blood.donation.entity.Donation;
import co.istab.blooddonationservice.domain.blood.user.entity.User;
import co.istab.blooddonationservice.infrastructure.database.elasticsearch.DonationDocument;
import co.istab.blooddonationservice.infrastructure.database.mysql.Entity.DonationEntity;
import co.istab.blooddonationservice.infrastructure.database.mysql.Entity.UserEntity;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface DonationDatabaseMapper {

    @Mapping(target = "donorId", source = "donor.id")
    @Mapping(target = "quantity", source = "quantity")
    Donation form(DonationEntity donationEntity);

    @Mapping(target = "donor", source = "donorId",  qualifiedByName = "mapUser")
    DonationEntity form(Donation donation);

    @Named("mapUser")
    default UserEntity mapUser(Integer donorId) {
        if (donorId == null) return null;
        UserEntity user = new UserEntity();
        user.setId(donorId);
        return user;
    }

    @Mapping(target = "donationId", source = "id")
    @Mapping(target = "status", source = "status")
    DonationDocument mapElastic(DonationEntity donation);


}
