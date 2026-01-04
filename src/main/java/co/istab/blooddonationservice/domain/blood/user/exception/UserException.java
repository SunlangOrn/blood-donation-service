package co.istab.blooddonationservice.domain.blood.user.exception;

import co.istab.blooddonationservice.share.exception.HttpException;
import org.springframework.http.HttpStatus;

public class UserException extends HttpException {

    public UserException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }

    public static UserException typeBloodRequired(){
        return new UserException(HttpStatus.BAD_REQUEST, "TYPE_BLOOD_REQUIRED");
    }


    public static  UserException emailRequired() {
        return new UserException(HttpStatus.BAD_REQUEST, "EMAIL_ID_REQUIRED");
    }

    public static  UserException roleNotFound() {
        return new UserException(HttpStatus.NOT_FOUND, "ROLE_NOT_FOUND");
    }

    public static  UserException notFound() {
        return new UserException(HttpStatus.NOT_FOUND, "USER_NOT_FOUND");
    }

    public static  UserException phoneNumberRequired() {
        return new UserException(HttpStatus.BAD_REQUEST, "PHONE_NUMBER_REQUIRED");
    }

    public static  UserException passwordRequired() {
        return new UserException(HttpStatus.BAD_REQUEST, "PASSWORD_REQUIRED");
    }

    public static  UserException passwordNonMatch() {
        return new UserException(HttpStatus.BAD_REQUEST, "PASSWORD_NOT_MATCH");
    }
    public static  UserException passwordNonSpace() {
        return new UserException(HttpStatus.BAD_REQUEST, "PASSWORD_NOT_SPACE");
    }

    public static UserException passwordIsTooShort() {
        return new UserException(HttpStatus.BAD_REQUEST, "PASSWORD_IS_TOO_SHORT");
    }

    public static UserException alreadyExist(){
        return new UserException(HttpStatus.CONFLICT,"THIS_PHONE_NUMBER_ALREADY_REGISTER");
    }

    public static UserException invalidFirstName(){
        return new UserException(HttpStatus.CONFLICT,"INVALID_FIRST_NAME");
    }

    public static UserException invalidLastName(){
        return new UserException(HttpStatus.CONFLICT,"INVALID_LAST_NAME");
    }



}
