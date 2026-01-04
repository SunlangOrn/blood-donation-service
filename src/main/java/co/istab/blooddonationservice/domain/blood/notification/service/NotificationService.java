package co.istab.blooddonationservice.domain.blood.notification.service;

import co.istab.blooddonationservice.domain.blood.notification.entity.Notification;
import co.istab.blooddonationservice.share.entity.Metadata;
import co.istab.blooddonationservice.share.entity.PaginationQuery;
import co.istab.blooddonationservice.share.entity.Paging;

public interface NotificationService {

    Notification createAndSendNotification(
            Integer userId,
            String title,
            String message,
            String type,
            Integer donationId,
            Integer donationActionId
    );

    Paging<Notification> list(Metadata metadata, PaginationQuery query);

    Paging<Notification> unread(Metadata metadata, PaginationQuery query);

    Notification viewNotification(Metadata metadata,Integer notificationId);

}
