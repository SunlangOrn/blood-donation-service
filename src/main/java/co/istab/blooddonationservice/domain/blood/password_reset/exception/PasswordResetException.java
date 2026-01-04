package co.istab.blooddonationservice.domain.blood.password_reset.exception;

import co.istab.blooddonationservice.share.exception.HttpException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PasswordResetException extends HttpException {

    public PasswordResetException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }

    public static PasswordResetException phoneNumberRequired() {
        return new PasswordResetException(HttpStatus.BAD_REQUEST, "Phone number is required");
    }

    public static PasswordResetException userNotFound() {
        return new PasswordResetException(HttpStatus.NOT_FOUND, "User not found with this phone number");
    }

    public static PasswordResetException userInactive() {
        return new PasswordResetException(HttpStatus.BAD_REQUEST, "User account is inactive");
    }

    public static PasswordResetException otpRequired() {
        return new PasswordResetException(HttpStatus.BAD_REQUEST, "OTP is required");
    }

    public static PasswordResetException invalidOtp() {
        return new PasswordResetException(HttpStatus.BAD_REQUEST, "Invalid OTP");
    }

    public static PasswordResetException otpExpired() {
        return new PasswordResetException(HttpStatus.BAD_REQUEST, "OTP has expired. Please request a new one");
    }

    public static PasswordResetException passwordRequired() {
        return new PasswordResetException(HttpStatus.BAD_REQUEST, "New password is required");
    }

    public static PasswordResetException passwordNotMatching() {
        return new PasswordResetException(HttpStatus.BAD_REQUEST, "Password must be match");
    }

    public static PasswordResetException passwordContainsSpace() {
        return new PasswordResetException(HttpStatus.BAD_REQUEST, "Password cannot contain spaces");
    }

    public static ResponseStatusException smsFailure() {
        return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to send OTP. Please try again");
    }
}
