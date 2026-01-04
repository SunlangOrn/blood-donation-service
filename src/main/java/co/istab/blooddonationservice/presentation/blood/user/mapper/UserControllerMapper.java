package co.istab.blooddonationservice.presentation.blood.user.mapper;

import co.istab.blooddonationservice.domain.blood.user.entity.User;
import co.istab.blooddonationservice.presentation.blood.user.model.request.UpdateUserProfileRequest;
import co.istab.blooddonationservice.presentation.blood.user.model.response.UserProfileResponse;
import org.mapstruct.*;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserControllerMapper {


    User from(UpdateUserProfileRequest request);

    @Mapping(target = "userId", source = "id")
    @Mapping(target = "cratedAt", source = "createdAt")
    @Mapping(target = "email", source = "email", defaultValue = "")
    @Mapping(target = "typeBlood", source = "typeBlood", defaultValue = "")
    @Mapping(target = "location", source = "location", defaultValue = "Not Specified")
    UserProfileResponse from(User user);


}
