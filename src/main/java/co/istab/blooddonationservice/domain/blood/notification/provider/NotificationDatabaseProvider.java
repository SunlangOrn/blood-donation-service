package co.istab.blooddonationservice.domain.blood.notification.provider;

import co.istab.blooddonationservice.domain.blood.notification.entity.Notification;
import co.istab.blooddonationservice.share.entity.PaginationQuery;
import co.istab.blooddonationservice.share.entity.Paging;

import java.util.Optional;

public interface NotificationDatabaseProvider {

    Paging<Notification> getAll(Integer userId ,PaginationQuery query);

    Paging<Notification> getUnread(Integer userId ,PaginationQuery query);

    Optional<Notification> getById(Integer Id);

    Notification save(Notification notification);

}
