package co.istab.blooddonationservice.presentation.blood.password_reset.mapper;

import co.istab.blooddonationservice.domain.blood.password_reset.entity.PasswordReset;
import co.istab.blooddonationservice.presentation.blood.password_reset.model.request.ForgetPasswordRequest;
import co.istab.blooddonationservice.presentation.blood.password_reset.model.request.PasswordResetRequest;
import co.istab.blooddonationservice.presentation.blood.password_reset.model.response.PasswordResetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface PasswordResetControllerMapper {

    PasswordResetResponse form(PasswordReset passwordReset);

    PasswordReset form(PasswordResetRequest passwordResetRequest);

    PasswordReset form(ForgetPasswordRequest forgetPasswordRequest);

}
