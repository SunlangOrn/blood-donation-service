package co.istab.blooddonationservice.domain.blood.notification.provider;

import co.istab.blooddonationservice.domain.blood.notification.entity.ReadNotification;

import java.util.Optional;

public interface ReadNotificationDatabaseProvider {

    Optional<ReadNotification> getUserIdAndNotificationId(Integer userId, Integer notificationId);

    ReadNotification save(ReadNotification readNotification);
}
