package co.istab.blooddonationservice.domain.blood.notification.exception;

import co.istab.blooddonationservice.share.exception.HttpException;
import org.springframework.http.HttpStatus;

public class NotificationException extends HttpException {

    public static NotificationException notFound() {
        return new NotificationException(HttpStatus.NOT_FOUND, "NOTIFICATION_NOT_FOUND");
    }

    public static NotificationException notFoundUserId() {
        return new NotificationException(HttpStatus.NOT_FOUND, "USER_NOT_FOUND");
    }

    public static NotificationException notFoundDonationId() {
        return new NotificationException(HttpStatus.NOT_FOUND, "DONATION_NOT_FOUND");
    }

    public static NotificationException notFoundDonationActionId() {
        return new NotificationException(HttpStatus.NOT_FOUND, "DONATION_ACTION_NOT_FOUND");
    }

    public NotificationException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
