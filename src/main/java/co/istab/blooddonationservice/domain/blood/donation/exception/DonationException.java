package co.istab.blooddonationservice.domain.blood.donation.exception;

import co.istab.blooddonationservice.domain.blood.password_reset.exception.PasswordResetException;
import co.istab.blooddonationservice.presentation.blood.donation.model.response.DonationResponse;
import co.istab.blooddonationservice.share.exception.HttpException;
import org.springframework.http.HttpStatus;

public class DonationException extends HttpException {

    public DonationException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }

    public static DonationException notFound() {
        return new DonationException(HttpStatus.NOT_FOUND, "DONATION_NOT FOUND");
    }

    public static DonationException alreadyExist() {
        return new DonationException(HttpStatus.BAD_REQUEST, "NAME_IS_Already_EXIST");
    }

    public static DonationException notOwnDonation() {
        return new DonationException(HttpStatus.FORBIDDEN, "YOU_CAN_ONLY_UPDATE_YOUR_DONATION");
    }

    public static DonationException alreadyDonated() {
        return new DonationException(HttpStatus.FORBIDDEN, "CANNOT_UPDATE_COMPLETED_DONATION");
    }

    public static DonationException cannotDelete() {
        return new DonationException(HttpStatus.FORBIDDEN, "YOU_CAN_ONLY_DELETE_YOUR_DONATION");
    }

    public static DonationException cannotDeleteCompleted() {
        return new DonationException(HttpStatus.FORBIDDEN, "CANNOT_DELETE_COMPLETED_DONATION");
    }

    public static DonationException notFoundDonor() {
        return new DonationException(HttpStatus.NOT_FOUND, "DONOR_NOT FOUND");
    }

}
