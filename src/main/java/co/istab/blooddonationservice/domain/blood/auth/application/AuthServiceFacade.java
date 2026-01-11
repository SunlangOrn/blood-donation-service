package co.istab.blooddonationservice.domain.blood.auth.application;

import co.istab.blooddonationservice.domain.blood.user.entity.Role;
import co.istab.blooddonationservice.domain.blood.user.entity.User;
import co.istab.blooddonationservice.domain.blood.user.entity.UserVerification;
import co.istab.blooddonationservice.domain.blood.user.exception.UserException;
import co.istab.blooddonationservice.domain.blood.user.provider.UserDatabaseProvider;
import co.istab.blooddonationservice.domain.blood.user.provider.UserVerificationDatabaseProvider;
import co.istab.blooddonationservice.domain.blood.auth.service.AuthService;
import co.istab.blooddonationservice.infrastructure.Config.Security.CustomerUserDetail;
import co.istab.blooddonationservice.presentation.blood.auth.mapper.AuthControllerMapper;
import co.istab.blooddonationservice.presentation.blood.auth.model.request.LoginRequest;
import co.istab.blooddonationservice.presentation.blood.auth.model.request.RefreshTokenRequest;
import co.istab.blooddonationservice.presentation.blood.auth.model.request.RegisterRequest;
import co.istab.blooddonationservice.presentation.blood.auth.model.request.VerifyRequest;
import co.istab.blooddonationservice.presentation.blood.auth.model.response.LoginResponse;
import co.istab.blooddonationservice.presentation.blood.auth.model.response.RegisterResponse;
import co.istab.blooddonationservice.share.exception.RestControllerAdviceExceptionHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceFacade implements AuthService {

    private final UserDatabaseProvider userDatabaseProvider;
    private final AuthControllerMapper userMapper;
    private final UserVerificationDatabaseProvider userVerificationDatabaseProvider;
    private final JwtEncoder jwtEncoder;
    private JwtEncoder jwtEncoderRefreshToken;

    private final PasswordEncoder passwordEncoder;

    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final String TOKEN_TYPE = "Bearer";
    private RestControllerAdviceExceptionHandler restControllerAdviceExceptionHandler;

    @Autowired
    @Qualifier("jwtEncoderRefreshToken")
    public void setJwtEncoderRefreshToken(JwtEncoder jwtEncoderRefreshToken) {
        this.jwtEncoderRefreshToken = jwtEncoderRefreshToken;
    }

    @Override
    public LoginResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {

        Authentication auth = new BearerTokenAuthenticationToken(refreshTokenRequest.getToken());
        auth = jwtAuthenticationProvider.authenticate(auth);

        String scope = auth.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        log.info("New Scope: {}", scope);
        log.info("Auth: {}", auth);

        Instant now = Instant.now();

        Jwt jwt = (Jwt) auth.getPrincipal();

        // Create access token claims set
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .id(jwt.getId())
                .issuedAt(now)
                .issuer("blood-donation-service")
                .audience(List.of("nextjs", "reactjs"))
                .subject("Access Token")
                .claim("scope", scope)
                .expiresAt(now.plus(30, ChronoUnit.MINUTES))
                .build();

        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(jwtClaimsSet);
        Jwt encodedJwt = jwtEncoder.encode(jwtEncoderParameters);

        String accessToken = encodedJwt.getTokenValue();
        String refreshToken = refreshTokenRequest.getToken();

        if (Duration.between(Instant.now(), jwt.getExpiresAt()).toDays() < 2) {

            //create refresh token claim set
            JwtClaimsSet JwtClaimsSetRefreshToken = JwtClaimsSet.builder()
                    .id(auth.getName())
                    .issuedAt(now)
                    .issuer("blood-donation-service")
                    .audience(List.of("nextjs", "reactjs"))
                    .subject("Refresh Token")
                    .claim("scope", scope)
                    .expiresAt(now.plus(7, ChronoUnit.DAYS))
                    .build();

            JwtEncoderParameters jwtEncoderParametersRefreshToken = JwtEncoderParameters.from(JwtClaimsSetRefreshToken);
            Jwt jwtRefreshToken = jwtEncoderRefreshToken.encode(jwtEncoderParametersRefreshToken);
            refreshToken = jwtRefreshToken.getTokenValue();
        }
        return LoginResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Login Successful")
                .data(LoginResponse.Token.of(
                        TOKEN_TYPE,
                        accessToken,
                        refreshToken))
                .build();
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        Authentication auth = new UsernamePasswordAuthenticationToken(
                request.getPhoneNumber(),
                request.getPassword()
        );
        auth = daoAuthenticationProvider.authenticate(auth);

        CustomerUserDetail userDetail = (CustomerUserDetail) auth.getPrincipal();
        User user = userDetail.getUser();

        String userId = String.valueOf(user.getId());
        String phoneNumber = user.getPhoneNumber();
        log.info("authenticated user: {}", auth.getAuthorities());

        String scope = auth.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        log.info("scope: {}", scope);

        Instant now = Instant.now();

        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .id(userId)
                .subject(phoneNumber)
                .claim("userId", userId)
                .claim("phoneNumber", phoneNumber)
                .issuedAt(now)
                .issuer("blood-donation-service")
                .audience(List.of("nextjs", "reactjs"))
                .claim("scope", scope)
                .expiresAt(now.plus(10, ChronoUnit.DAYS))
                .build();

        JwtClaimsSet jwtClaimsSetRefreshToken = JwtClaimsSet.builder()
                .id(userId)
                .subject(phoneNumber)
                .claim("userId", String.valueOf(user.getId()))
                .claim("phoneNumber", phoneNumber)
                .issuedAt(now)
                .issuer("blood-donation-service")
                .audience(List.of("nextjs", "reactjs"))
                .claim("scope", scope)
                .expiresAt(now.plus(7, ChronoUnit.DAYS))
                .build();

        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(jwtClaimsSet);
        Jwt jwt = jwtEncoder.encode(jwtEncoderParameters);

        JwtEncoderParameters jwtEncoderParametersRefreshToken = JwtEncoderParameters.from(jwtClaimsSetRefreshToken);
        Jwt jwtRefreshToken = jwtEncoder.encode(jwtEncoderParametersRefreshToken);

        String accessToken = jwt.getTokenValue();
        String refreshToken = jwtRefreshToken.getTokenValue();

        return LoginResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Login Successful")
                .data(LoginResponse.Token.of(
                        TOKEN_TYPE,
                        accessToken,
                        refreshToken))
                .build();
    }

    @Override
    public void verify(String phoneNumber, VerifyRequest verifyRequest) {

        User user = userDatabaseProvider.getByPhoneNumber(phoneNumberForSec(phoneNumber))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid phone number"));

        UserVerification verification = userVerificationDatabaseProvider
                .findByUserId(user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Verification not found"));

        if (!verification.getVerificationCode().equals(verifyRequest.getVerificationCode())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid verification code");
        }

        if (LocalDateTime.now().isAfter(verification.getExpiryTime())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Verification code expired");
        }

        user.setIsVerifyUser(true);
        user.setIsVerifyOtp(true);
        userDatabaseProvider.save(user);

        userVerificationDatabaseProvider.delete(verification);
    }

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) throws ResponseStatusException {

        String phoneNumber = phoneNumberForSec(registerRequest.getPhoneNumber());
        String password = registerRequest.getPassword().trim();
        String firstName = registerRequest.getFirstName().trim();
        String lastName = registerRequest.getLastName().trim();
        String confirmPassword = registerRequest.getConfirmPassword().trim();


        if (phoneNumber.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "All fields are required");
        }

        if (!password.equals(confirmPassword)) throw UserException.passwordNonMatch();
        if (password.length() < 8) throw UserException.passwordIsTooShort();
        if (password.contains(" ")) throw UserException.passwordNonSpace();
        if (!isValidName(firstName)) throw UserException.invalidFirstName();
        if (!isValidName(lastName)) throw UserException.invalidLastName();

        userDatabaseProvider.getByPhoneNumber(phoneNumber)
                .ifPresent(u -> { throw UserException.alreadyExist(); });


        User user = userMapper.form(registerRequest);
        Role userRole = userDatabaseProvider.getRoleId(1)
                .orElseThrow(UserException::roleNotFound);
        user.setRole(userRole);

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhoneCode("+855");
        user.setCreatedAt(new Date());
        user.setPhoneNumber(phoneNumber);
        user.setPassword(passwordEncoder.encode(password));
        user.setStatus(true);
        user.setIsVerifyUser(false);
        user.setIsVerifyOtp(false);
        User userSave =userDatabaseProvider.save(user);

        final String otpCode = "123456";
        UserVerification userVerification = new UserVerification();
        userVerification.setUserId(userSave.getId());
        userVerification.setVerificationCode(otpCode);
        userVerification.setCreatedAt(new Date());
        userVerification.setExpiryTime(LocalDateTime.now().plusMinutes(30));
        userVerificationDatabaseProvider.save(userVerification);
        return  RegisterResponse.builder()
                .verificationCode(otpCode)
                .build();
    }

    private String phoneNumberForSec(String phoneNumber) {
        if (phoneNumber == null) return null;
        String normalized = phoneNumber.trim().replaceAll("[\\s\\-()]", "");
        if (normalized.startsWith("0")) normalized = normalized.substring(1);
        return normalized;
    }

    private boolean isValidName(String name) {
        return name.matches("^[A-Za-zÀ-ÖØ-öø-ÿ'\\-]{2,50}$");
    }

}
