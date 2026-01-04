package co.istab.blooddonationservice.share.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HttpBodyErrorResponse {
  private String type;
  private String code;
  private String message;
  private String error;
  private Map<String, String> bodyRequestError;
}
