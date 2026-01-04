package co.istab.blooddonationservice.domain.blood.password_reset.service;

import co.istab.blooddonationservice.domain.blood.password_reset.entity.PasswordReset;
import co.istab.blooddonationservice.presentation.blood.password_reset.model.request.ForgetPasswordRequest;
import co.istab.blooddonationservice.presentation.blood.password_reset.model.request.PasswordResetRequest;
import co.istab.blooddonationservice.presentation.blood.password_reset.model.response.ForgetPasswordResponse;
import co.istab.blooddonationservice.presentation.blood.password_reset.model.response.PasswordResetResponse;

public interface PasswordRestService {

    ForgetPasswordResponse forgetPassword(ForgetPasswordRequest forgetPasswordRequest);

    PasswordReset passwordReset(PasswordResetRequest passwordResetRequest, Integer id);
}
