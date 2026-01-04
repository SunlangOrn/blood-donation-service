package co.istab.blooddonationservice.domain.file;

import co.istab.blooddonationservice.share.exception.HttpException;
import org.springframework.http.HttpStatus;

public class FileException extends HttpException {

    public FileException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }

    public static FileException required() {
        return new FileException(HttpStatus.BAD_REQUEST, "FILE_IS_REQUIRED");
    }

    public static FileException notFound() {
        return new FileException(HttpStatus.NOT_FOUND, "FILE_IS_NOT_FOUND");
    }
}
