package co.istab.blooddonationservice.infrastructure.database.mapper;

import co.istab.blooddonationservice.domain.blood.user.entity.User;
import co.istab.blooddonationservice.infrastructure.database.mysql.Entity.UserEntity;
import org.mapstruct.*;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface UserDatabaseMapper {

    @Mapping(target = "role", source = "role")
    @Mapping(target = "file.id", source = "mediaId")
    UserEntity form(User user);

    @Mapping(target = "role", source = "role")
    @Mapping(target = "mediaId", source = "file.id")
    User form(UserEntity userEntity);

}
