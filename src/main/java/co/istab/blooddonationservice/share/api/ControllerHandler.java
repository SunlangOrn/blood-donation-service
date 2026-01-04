package co.istab.blooddonationservice.share.api;

import co.istab.blooddonationservice.presentation.blood.donation.model.response.DonationResponse;
import co.istab.blooddonationservice.share.entity.HttpBodyPagingResponse;
import co.istab.blooddonationservice.share.entity.HttpBodyResponse;
import co.istab.blooddonationservice.share.exception.HttpException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

public class ControllerHandler {

  public static <T> ResponseEntity<HttpBodyResponse<T>> responsePaging(
      T data, HttpBodyPagingResponse pagingResponse) {
    return responsePaging(HttpStatus.OK, HttpStatus.OK.name(), data, pagingResponse);
  }

  public static <T> ResponseEntity<HttpBodyResponse<T>> responsePaging(
      HttpStatus httpStatus, String message, T data, HttpBodyPagingResponse pagingResponse) {
    return response(httpStatus, message, data, pagingResponse);
  }

  public static <T> ResponseEntity<HttpBodyResponse<T>> responseSucceed(String message, T data) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(
            HttpBodyResponse.<T>builder()
                .status(HttpStatus.OK.value())
                .message(message)
                .data(data)
                .build());
  }

  public static ResponseEntity<HttpBodyResponse<Object>> responseSucceed(DonationResponse from) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(
            HttpBodyResponse.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .build());
  }

    public static <T> ResponseEntity<HttpBodyResponse<T>> responseSucceed(T data) {
        return responseSucceed(HttpStatus.OK.name(), data);
    }

  public static <T> ResponseEntity<HttpBodyResponse<T>> responseCreated(T data) {
    return responseCreated(HttpStatus.CREATED.name(), data);
  }

  public static ResponseEntity<HttpBodyResponse<Object>> responseCreated() {
    return responseCreated(HttpStatus.CREATED.name());
  }

  public static ResponseEntity<HttpBodyResponse<Object>> responseCreated(String message) {
    return response(HttpStatus.CREATED, message);
  }

  public static <T> ResponseEntity<HttpBodyResponse<T>> responseCreated(String message, T data) {
    return response(HttpStatus.CREATED, message, data);
  }

  public static ResponseEntity<Void> responseDeleted() {
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  public static ResponseEntity<HttpBodyResponse<Object>> response(
      HttpStatus httpStatus, String message) {
    return ResponseEntity.status(httpStatus)
        .body(HttpBodyResponse.builder().status(httpStatus.value()).message(message).build());
  }

  public static <T> ResponseEntity<HttpBodyResponse<T>> response(
      HttpStatus httpStatus, String message, T data) {
    return ResponseEntity.status(httpStatus)
        .body(
            HttpBodyResponse.<T>builder()
                .status(httpStatus.value())
                .message(message)
                .data(data)
                .build());
  }

  public static <T> ResponseEntity<HttpBodyResponse<T>> response(
      HttpStatus httpStatus, String message, T data, HttpBodyPagingResponse pagingResponse) {
    return ResponseEntity.status(httpStatus)
        .body(
            HttpBodyResponse.<T>builder()
                .status(httpStatus.value())
                .message(message)
                .data(data)
                .paging(pagingResponse)
                .build());
  }

  public static Integer requiredHeaderInt(String headerName, String message) {
    return Integer.parseInt(requiredHeader(headerName, message));
  }

  public static String requiredHeader(String headerName, String message) {
    ServletRequestAttributes attributes =
        (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    if (Objects.isNull(attributes)) throw new HttpException(HttpStatus.BAD_REQUEST, message);

    HttpServletRequest request = attributes.getRequest();
    String headerValue = request.getHeader(headerName);
    if (Objects.isNull(headerValue)) throw new HttpException(HttpStatus.BAD_REQUEST, message);

    return request.getHeader(headerName);
  }

  public static String getHeader(String headerName) {
    ServletRequestAttributes attributes =
        (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    if (Objects.isNull(attributes)) return null;

    HttpServletRequest request = attributes.getRequest();
    return request.getHeader(headerName);
  }
}
