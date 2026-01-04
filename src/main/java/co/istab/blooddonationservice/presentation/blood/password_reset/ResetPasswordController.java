package co.istab.blooddonationservice.presentation.blood.password_reset;

import co.istab.blooddonationservice.domain.blood.password_reset.entity.PasswordReset;
import co.istab.blooddonationservice.domain.blood.password_reset.service.PasswordRestService;
import co.istab.blooddonationservice.presentation.blood.password_reset.mapper.PasswordResetControllerMapper;
import co.istab.blooddonationservice.presentation.blood.password_reset.model.request.ForgetPasswordRequest;
import co.istab.blooddonationservice.presentation.blood.password_reset.model.request.PasswordResetRequest;
import co.istab.blooddonationservice.presentation.blood.password_reset.model.response.ForgetPasswordResponse;
import co.istab.blooddonationservice.presentation.blood.password_reset.model.response.PasswordResetResponse;
import co.istab.blooddonationservice.share.entity.HttpBodyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import static co.istab.blooddonationservice.share.api.ControllerHandler.responseCreated;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class ResetPasswordController {

    private final PasswordRestService service;
    private final PasswordResetControllerMapper mapper;

    @PostMapping("/reset-password/{id}")
    public ResponseEntity<HttpBodyResponse<PasswordResetResponse>> resetPassword(
            @RequestBody PasswordResetRequest request, @PathVariable Integer id) {

        PasswordReset passwordResetEntity = service.passwordReset(request, id);
        PasswordResetResponse response = mapper.form(passwordResetEntity);
        return responseCreated(response);
    }

    @PostMapping("/forget-password")
    public ResponseEntity<HttpBodyResponse<ForgetPasswordResponse>> register(@RequestBody ForgetPasswordRequest request) {
        ForgetPasswordResponse response = service.forgetPassword(request);
        return responseCreated(response);
    }


}
