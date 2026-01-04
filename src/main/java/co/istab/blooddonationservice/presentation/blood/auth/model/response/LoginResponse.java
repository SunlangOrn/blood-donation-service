package co.istab.blooddonationservice.presentation.blood.auth.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Builder
public class LoginResponse {
  private int status;
  private String message;
  private Token data;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor(staticName = "of")
  public static class Token {
    private String tokenType;
    private String accessToken;
    private String refreshToken;
  }
}
