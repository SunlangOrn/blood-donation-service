package co.istab.blooddonationservice.share.exception;

import lombok.Getter;

@Getter
public enum ErrorConstantException {
  ERROR("An unexpected error occurred"),
  UNKNOWN_ERROR(null),
  VALIDATION_ERROR("Validation error");

  private final String message;

  ErrorConstantException(String message) {
    this.message = message;
  }
}
