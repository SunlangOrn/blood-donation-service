package co.istab.blooddonationservice.infrastructure.database.mapper;

import co.istab.blooddonationservice.domain.blood.notification.entity.ReadNotification;
import co.istab.blooddonationservice.infrastructure.database.mysql.Entity.ReadNotificationEntity;
import org.mapstruct.*;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface ReadNotificationDatabaseMapper {

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "notification.id", source = "notificationId")
    ReadNotificationEntity from(ReadNotification readNotification);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "notificationId", source = "notification.id")
    ReadNotification from(ReadNotificationEntity readNotificationEntity);
}
