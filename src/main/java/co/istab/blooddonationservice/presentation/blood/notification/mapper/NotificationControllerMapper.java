package co.istab.blooddonationservice.presentation.blood.notification.mapper;

import co.istab.blooddonationservice.domain.blood.notification.entity.Notification;
import co.istab.blooddonationservice.presentation.blood.notification.model.response.NotificationResponse;
import co.istab.blooddonationservice.presentation.blood.notification.model.response.NotificationResponseDetail;
import org.mapstruct.*;

import java.util.Date;


@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)

public interface NotificationControllerMapper {

    @Mapping(target = "id", source = "notification.id")
    @Mapping(target = "isRead", source = "isRead")
    @Mapping(target = "readAt", source = "readAt")
    @Mapping(target = "referenceDonationId", source = "notification.referenceDonation.id")
    @Mapping(target = "referenceActionId", source = "notification.referenceAction.id")
    @Mapping(target = "referencePostId", source = "notification.referencePost.id")
    NotificationResponseDetail mapDetail(Notification notification, Boolean isRead, Date readAt);

    @Mapping(target = "notificationId", source = "notification.id")
    NotificationResponse mapResponse(Notification notification, Boolean isRead, Date readAt);

    default Integer getReferenceActionId(Notification notification) {
        if (notification.getReferenceAction()!= null && notification.getReferenceAction().getId() != null) {
            return notification.getReferenceAction().getId();
        }
        return null;
    }

    default Integer getReferenceDonationId(Notification notification) {
        if (notification.getReferenceDonation() != null && notification.getReferenceDonation().getId() != null) {
            return notification.getReferenceDonation().getId();
        }
        return null;
    }

}
