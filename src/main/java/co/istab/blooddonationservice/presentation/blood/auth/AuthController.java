package co.istab.blooddonationservice.presentation.blood.auth;

import co.istab.blooddonationservice.domain.blood.auth.service.AuthService;
import co.istab.blooddonationservice.presentation.blood.auth.model.request.LoginRequest;
import co.istab.blooddonationservice.presentation.blood.auth.model.request.RefreshTokenRequest;
import co.istab.blooddonationservice.presentation.blood.auth.model.request.RegisterRequest;
import co.istab.blooddonationservice.presentation.blood.auth.model.request.VerifyRequest;
import co.istab.blooddonationservice.presentation.blood.auth.model.response.LoginResponse;
import co.istab.blooddonationservice.presentation.blood.auth.model.response.RegisterResponse;
import co.istab.blooddonationservice.presentation.blood.user.UserController;
import co.istab.blooddonationservice.presentation.blood.user.mapper.UserControllerMapper;
import co.istab.blooddonationservice.share.entity.HttpBodyResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static co.istab.blooddonationservice.share.api.ControllerHandler.responseCreated;
import static co.istab.blooddonationservice.share.api.ControllerHandler.responseSucceed;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserControllerMapper mapper;

    @PostMapping("/refresh")
    public ResponseEntity<HttpBodyResponse<LoginResponse>> refreshToken(@Valid@RequestBody RefreshTokenRequest refreshTokenRequest) {
        LoginResponse  response = authService.refreshToken(refreshTokenRequest);
        return responseCreated(response);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/verify")
    public ResponseEntity<HttpBodyResponse<Object>> verify(
            @Valid @RequestBody VerifyRequest verifyRequest) {
        authService.verify(verifyRequest.getPhoneNumber(), verifyRequest);
        return responseCreated("Verification successful");
    }

    @PostMapping("/register")
    public ResponseEntity<HttpBodyResponse<RegisterResponse>> register(@Valid @RequestBody RegisterRequest registerRequest) {
        RegisterResponse response = authService.register(registerRequest);
        return responseCreated(response);
    }
}
