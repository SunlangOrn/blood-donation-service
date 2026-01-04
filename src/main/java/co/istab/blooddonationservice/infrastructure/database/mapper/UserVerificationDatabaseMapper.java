package co.istab.blooddonationservice.infrastructure.database.mapper;

import co.istab.blooddonationservice.domain.blood.user.entity.UserVerification;
import co.istab.blooddonationservice.infrastructure.database.mysql.Entity.UserVerificationEntity;
import org.mapstruct.*;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface UserVerificationDatabaseMapper {

    @Mapping(target = "user.id", source = "userId")
    UserVerificationEntity form(UserVerification userVerification);

    @Mapping(target = "userId", source = "user.id")
    UserVerification form(UserVerificationEntity userVerificationEntity);
}
