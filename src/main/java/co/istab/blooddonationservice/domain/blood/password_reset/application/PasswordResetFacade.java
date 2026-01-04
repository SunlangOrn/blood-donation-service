package co.istab.blooddonationservice.domain.blood.password_reset.application;

import co.istab.blooddonationservice.domain.blood.password_reset.entity.PasswordReset;
import co.istab.blooddonationservice.domain.blood.password_reset.exception.PasswordResetException;
import co.istab.blooddonationservice.domain.blood.password_reset.provider.PasswordResetDatabaseProvider;
import co.istab.blooddonationservice.domain.blood.password_reset.service.PasswordRestService;
import co.istab.blooddonationservice.domain.blood.user.entity.User;
import co.istab.blooddonationservice.domain.blood.user.provider.UserDatabaseProvider;
import co.istab.blooddonationservice.presentation.blood.password_reset.mapper.PasswordResetControllerMapper;
import co.istab.blooddonationservice.presentation.blood.password_reset.model.request.ForgetPasswordRequest;
import co.istab.blooddonationservice.presentation.blood.password_reset.model.request.PasswordResetRequest;
import co.istab.blooddonationservice.presentation.blood.password_reset.model.response.ForgetPasswordResponse;
import co.istab.blooddonationservice.presentation.blood.password_reset.model.response.PasswordResetResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class PasswordResetFacade implements PasswordRestService {

    private final PasswordResetDatabaseProvider passwordResetDatabaseProvider;
    private final UserDatabaseProvider userDatabaseProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public PasswordReset passwordReset(PasswordResetRequest request, Integer id) {

        PasswordReset passwordReset = passwordResetDatabaseProvider.findById(id).orElseThrow(PasswordResetException::userNotFound);
         if(!passwordReset.getCode().equals(passwordReset.getCode())) {
             throw PasswordResetException.invalidOtp();
         }
         if(passwordReset.getExpiryTime().isBefore(LocalTime.now())){
            throw PasswordResetException.otpExpired();
         }
         if(!request.getNewPassword().equals(request.getConfirmPassword())){
             throw PasswordResetException.passwordNotMatching();
         }

         User user = userDatabaseProvider.getUserById(id)
                 .orElseThrow(PasswordResetException::userNotFound);
         user.setPassword(passwordEncoder.encode(request.getNewPassword()));
         userDatabaseProvider.save(user);

         passwordReset.setDeletedAt(new Date());
         passwordReset.setModifiedAt(new Date());

        return passwordResetDatabaseProvider.save(passwordReset);
    }


    @Override
    public ForgetPasswordResponse forgetPassword(ForgetPasswordRequest forgetPasswordRequest) {

        if(forgetPasswordRequest.getPhoneNumber() == null || forgetPasswordRequest.getPhoneNumber().isEmpty()) {
            throw PasswordResetException.passwordRequired();
        }

        String phoneNumber = forgetPasswordRequest.getPhoneNumber()
                .trim()
                .replaceAll("[\\s\\-()]", ""); // remove spaces, -, ()

        if (phoneNumber.startsWith("0")) {
            phoneNumber = phoneNumber.substring(1);
        }
        User user = userDatabaseProvider.getByPhoneNumber(phoneNumber)
                .orElseThrow(PasswordResetException::userNotFound);

        final String otpCode = "123456";
        PasswordReset passwordReset = new PasswordReset();
        passwordReset.setUserId(user.getId());
        passwordReset.setCode(otpCode);
        passwordReset.setExpiryTime(LocalTime.now().plus(30, ChronoUnit.MINUTES));
        passwordReset.setCreatedAt(new Date());
        passwordResetDatabaseProvider.save(passwordReset);
        return ForgetPasswordResponse.builder()
                .otpCode(otpCode)
                .build();
    }


}
