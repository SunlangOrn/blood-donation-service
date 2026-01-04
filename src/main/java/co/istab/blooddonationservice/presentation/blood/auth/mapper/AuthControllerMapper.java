package co.istab.blooddonationservice.presentation.blood.auth.mapper;

import co.istab.blooddonationservice.domain.blood.user.entity.User;
import co.istab.blooddonationservice.presentation.blood.auth.model.request.RegisterRequest;
import co.istab.blooddonationservice.presentation.blood.auth.model.response.LoginResponse;
import co.istab.blooddonationservice.presentation.blood.auth.model.response.RegisterResponse;
import org.mapstruct.*;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface AuthControllerMapper {

    @Mapping(target = "status", expression = "java(user.getStatus() != null && user.getStatus() ? 1 : 0)")
    LoginResponse form(User user);

    RegisterResponse form(String otpCode);

    User form(RegisterRequest userRegisterRequest);

}
