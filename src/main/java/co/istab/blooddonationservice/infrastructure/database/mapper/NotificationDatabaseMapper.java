package co.istab.blooddonationservice.infrastructure.database.mapper;

import co.istab.blooddonationservice.domain.blood.notification.entity.Notification;
import co.istab.blooddonationservice.infrastructure.database.mysql.Entity.NotificationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface NotificationDatabaseMapper {

    Notification from(NotificationEntity entity);

    NotificationEntity from(Notification notification);

}
