package co.istab.blooddonationservice.domain.blood.cateory.exception;

import co.istab.blooddonationservice.share.exception.HttpException;
import org.springframework.http.HttpStatus;

public class CategoryException extends HttpException {

    public static CategoryException notFound() {
        return new CategoryException(HttpStatus.BAD_REQUEST, "CATEGORY_NOT_FOUND");
    }

    public static CategoryException alreadyExists() {
        return new CategoryException(HttpStatus.BAD_REQUEST, "CATEGORY_ALREADY_EXISTS");
    }

    public CategoryException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
