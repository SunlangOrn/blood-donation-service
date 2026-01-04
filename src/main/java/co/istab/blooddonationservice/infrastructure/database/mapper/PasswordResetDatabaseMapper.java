package co.istab.blooddonationservice.infrastructure.database.mapper;
import co.istab.blooddonationservice.domain.blood.password_reset.entity.PasswordReset;
import co.istab.blooddonationservice.infrastructure.database.mysql.Entity.PasswordResetEntity;
import org.mapstruct.*;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface PasswordResetDatabaseMapper {

    @Mapping(target = "user.id", source = "userId")
    PasswordResetEntity form(PasswordReset passwordReset);

    @Mapping(target = "userId", source = "user.id")
    PasswordReset form(PasswordResetEntity passwordResetEntity);
}
