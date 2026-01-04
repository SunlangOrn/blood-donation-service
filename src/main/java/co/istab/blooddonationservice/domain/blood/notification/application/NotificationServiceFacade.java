package co.istab.blooddonationservice.domain.blood.notification.application;

import co.istab.blooddonationservice.domain.blood.donation.entity.Donation;
import co.istab.blooddonationservice.domain.blood.donation.provider.DonationDatabaseProvider;
import co.istab.blooddonationservice.domain.blood.donation_action.entity.DonationAction;
import co.istab.blooddonationservice.domain.blood.donation_action.provider.DonationActionDatabaseProvider;
import co.istab.blooddonationservice.domain.blood.notification.entity.Notification;
import co.istab.blooddonationservice.domain.blood.notification.entity.ReadNotification;
import co.istab.blooddonationservice.domain.blood.notification.exception.NotificationException;
import co.istab.blooddonationservice.domain.blood.notification.provider.NotificationDatabaseProvider;
import co.istab.blooddonationservice.domain.blood.notification.provider.ReadNotificationDatabaseProvider;
import co.istab.blooddonationservice.domain.blood.notification.service.NotificationService;
import co.istab.blooddonationservice.domain.blood.notification.service.OneSignalService;
import co.istab.blooddonationservice.domain.blood.user.entity.User;
import co.istab.blooddonationservice.domain.blood.user.provider.UserDatabaseProvider;
import co.istab.blooddonationservice.share.entity.Metadata;
import co.istab.blooddonationservice.share.entity.PaginationQuery;
import co.istab.blooddonationservice.share.entity.Paging;
import co.istab.blooddonationservice.share.handler.metadata.MetadataHandler;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceFacade implements NotificationService {

    private final OneSignalService oneSignalService;
    private final NotificationDatabaseProvider notificationProvider;
    private final UserDatabaseProvider userProvider;
    private final ReadNotificationDatabaseProvider readNotificationProvider;
    private final DonationActionDatabaseProvider donationActionProvider;
    private final DonationDatabaseProvider donationProvider;

    @Transactional
    @MetadataHandler
    @Override
    public Notification createAndSendNotification(
            Integer userId, String title,
            String message, String type,
            Integer donationId, Integer donationActionId)
    {


        User user = userProvider.getUserById(userId)
                .orElseThrow(NotificationException::notFoundUserId);

        Donation donation = null;
        if (donationId != null) {
            donation = donationProvider.getById(donationId)
                    .orElseThrow(NotificationException::notFoundDonationId);
        }

        DonationAction donationAction = null;
        if (donationActionId != null) {
            donationAction = donationActionProvider.getById(donationActionId)
                    .orElseThrow(NotificationException::notFoundDonationActionId);
        }

        Notification noti = new Notification();
        noti.setTitle(title);
        noti.setMessage(message);
        noti.setType(type);
        noti.setUser(user);
        noti.setReferenceDonation(donation);
        noti.setReferenceAction(donationAction);
        noti.setCreatedAt(new Date());

        Notification savedNotification = notificationProvider.save(noti);

        // Prepare data for push notification
        Map<String, Object> data = new HashMap<>();
        data.put("type", type);
        data.put("notification_id", savedNotification.getId());

        if (donationId != null) {
            data.put("donation_id", donationId);
        }
        if (donationActionId != null) {
            data.put("donation_action_id", donationActionId);
        }

        oneSignalService.sendNotification(
                userId.toString(),
                title,
                message,
                data
        );
        return savedNotification;
    }

    @Override
    @MetadataHandler
    @Transactional
    public Paging<Notification> list(Metadata metadata, PaginationQuery query) {

        Integer userId = Integer.parseInt(metadata.getUserId());
        return notificationProvider.getAll(userId, query);
    }

    @Override
    @MetadataHandler
    public Paging<Notification> unread(Metadata metadata, PaginationQuery query) {
        return notificationProvider.getUnread(Integer.parseInt(metadata.getUserId()), query);
    }

    @Override
    @Transactional
    @MetadataHandler
    public Notification viewNotification(Metadata metadata,Integer notificationId) {

        Integer userId = Integer.parseInt(metadata.getUserId());

        Notification notification = notificationProvider.getById(notificationId)
                .orElseThrow(NotificationException::notFound);

        if (!notification.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "CAN VIEW ONLY YOUR OWN NOTIFICATIONS");
        }

        // Check if already read or not
        Optional<ReadNotification> readStatus = readNotificationProvider
                .getUserIdAndNotificationId(userId, notificationId);

        if (!readStatus.isPresent()) {
            ReadNotification newRead = ReadNotification.builder()
                    .userId(userId)
                    .notificationId(notificationId)
                    .readAt(new Date())
                    .createdAt(new Date())
                    .build();
            readNotificationProvider.save(newRead);
        }
        return notification;
    }
}