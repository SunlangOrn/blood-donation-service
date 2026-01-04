package co.istab.blooddonationservice.domain.blood.donation_action.exception;

import co.istab.blooddonationservice.share.exception.HttpException;
import org.springframework.http.HttpStatus;


public class DonationActionException extends HttpException {

    public DonationActionException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }

    public static DonationActionException notAllow() {
        return new DonationActionException(HttpStatus.FORBIDDEN, "YOU_CAN_ONLY_CANCEL_YOUR_OWN_DONATION_OFFERS");
    }

    public static DonationActionException notFound() {
        return new DonationActionException(HttpStatus.NOT_FOUND, "DONATION_ACTION_NOT_FOUND");
    }

    public static DonationActionException acceptFail() {
        return new DonationActionException(HttpStatus.FORBIDDEN, "ONLY_THE_REQUESTER_CREATE_CAN_ACCEPT_THAT");
    }
    public static DonationActionException cannotAccess() {
        return new DonationActionException(HttpStatus.FORBIDDEN, "YOU_CAN_ONLY_ACCESS_YOUR_OWN_DONATION_ACTION");
    }

}
