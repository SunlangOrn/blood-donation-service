package co.istab.blooddonationservice.share.exception;

import co.istab.blooddonationservice.presentation.blood.auth.model.response.LoginResponse;
import co.istab.blooddonationservice.share.entity.HttpBodyErrorResponse;
import co.istab.blooddonationservice.share.entity.HttpBodyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.naming.AuthenticationException;
import java.util.*;

@RestControllerAdvice
@RequiredArgsConstructor
public class RestControllerAdviceExceptionHandler {

  @ExceptionHandler(value = HttpException.class)
  public ResponseEntity<Object> handleDomainException(HttpException exception) {

    ErrorConstantException errorConstantException =
        Optional.ofNullable(exception.getError()).orElse(ErrorConstantException.UNKNOWN_ERROR);

    String exceptionMessage =
        Optional.ofNullable(exception.getMessage())
            .orElse(ErrorConstantException.ERROR.getMessage());

    HttpBodyErrorResponse httpBodyErrorResponse =
        createHttpBodyErrorResponse(exception, errorConstantException);

    HttpBodyResponse<Object> response =
        HttpBodyResponse.builder()
            .status(exception.getHttpStatus().value())
            .message(exceptionMessage)
            .error(httpBodyErrorResponse)
            .build();

    return ResponseEntity.status(exception.getHttpStatus()).body(response);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<Object> handleValidationExceptions(
      MethodArgumentNotValidException exception) {

    Map<String, String> validationErrors =
        getValidationError(exception.getBindingResult().getAllErrors());

    HttpBodyErrorResponse httpBodyErrorResponse =
        createHttpBodyErrorResponse(
            exception,
            ErrorConstantException.VALIDATION_ERROR,
            List.of(ErrorConstantException.VALIDATION_ERROR.getMessage()),
            validationErrors);

    HttpBodyResponse<Object> response =
        HttpBodyResponse.builder()
            .status(HttpStatus.BAD_REQUEST.value())
            .message(ErrorConstantException.VALIDATION_ERROR.getMessage())
            .error(httpBodyErrorResponse)
            .build();

    return ResponseEntity.badRequest().body(response);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleException(Exception exception) {

    String messageError =
        Optional.ofNullable(exception.getMessage())
            .orElse(ErrorConstantException.ERROR.getMessage());

    HttpBodyErrorResponse httpBodyErrorResponse =
        createHttpBodyErrorResponse(exception, ErrorConstantException.UNKNOWN_ERROR);

    HttpBodyResponse<Object> response =
        HttpBodyResponse.builder()
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .message(messageError)
            .error(httpBodyErrorResponse)
            .build();

    if (exception instanceof MethodArgumentTypeMismatchException) {
      response.setStatus(HttpStatus.NOT_FOUND.value());

      response.setMessage("NOT_FOUND");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<LoginResponse> handleBadCredentialsException() {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(LoginResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("Invalid username or password")
                    .build());
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<LoginResponse> handleAuthenticationException() {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(LoginResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("Invalid username or password")
                    .build());
  }

  private HttpBodyErrorResponse createHttpBodyErrorResponse(
      Exception exception, ErrorConstantException errorConstantException) {
    return createHttpBodyErrorResponse(
        exception, errorConstantException, Collections.emptyList(), null);
  }

  private HttpBodyErrorResponse createHttpBodyErrorResponse(
      Exception exception,
      ErrorConstantException errorConstantException,
      List<String> errors,
      Map<String, String> bodyRequest) {
    StackTraceElement[] stackTraceElements =
        Optional.ofNullable(exception.getCause())
            .map(Throwable::getStackTrace)
            .orElse(exception.getStackTrace());

    String message = errors.stream().findFirst().orElse(errorConstantException.getMessage());

    return HttpBodyErrorResponse.builder()
        .type(exception.getClass().getSimpleName())
        .code(errorConstantException.name())
        .message(message)
        .error(String.valueOf(Arrays.stream(stackTraceElements).findFirst().orElse(null)))
        .bodyRequestError(bodyRequest)
        .build();
  }

  private Map<String, String> getValidationError(List<ObjectError> objectErrors) {
    Map<String, String> bodyRequestError = new HashMap<>();
    for (ObjectError objectError : objectErrors) {
      if (objectError instanceof FieldError fieldError) {
        bodyRequestError.put(fieldError.getField(), fieldError.getDefaultMessage());
      }
    }

    return bodyRequestError;
  }
}
