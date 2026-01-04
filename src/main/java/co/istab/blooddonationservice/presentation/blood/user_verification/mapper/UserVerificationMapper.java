package co.istab.blooddonationservice.presentation.blood.user_verification.mapper;

import co.istab.blooddonationservice.domain.blood.user.entity.UserVerification;
import co.istab.blooddonationservice.presentation.blood.auth.model.request.VerifyRequest;
import org.mapstruct.*;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface UserVerificationMapper {

    UserVerification form(VerifyRequest verifyRequest);
}
