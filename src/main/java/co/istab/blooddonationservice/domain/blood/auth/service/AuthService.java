package co.istab.blooddonationservice.domain.blood.auth.service;

import co.istab.blooddonationservice.presentation.blood.auth.model.request.LoginRequest;
import co.istab.blooddonationservice.presentation.blood.auth.model.request.RefreshTokenRequest;
import co.istab.blooddonationservice.presentation.blood.auth.model.request.RegisterRequest;
import co.istab.blooddonationservice.presentation.blood.auth.model.request.VerifyRequest;
import co.istab.blooddonationservice.presentation.blood.auth.model.response.LoginResponse;
import co.istab.blooddonationservice.presentation.blood.auth.model.response.RegisterResponse;
import org.springframework.web.server.ResponseStatusException;

public interface AuthService {

    LoginResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

    void verify(String phonNumber,VerifyRequest verifyRequest);

    LoginResponse login(LoginRequest request);

    RegisterResponse register(RegisterRequest registerRequest) throws ResponseStatusException;
}
