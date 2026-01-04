package co.istab.blooddonationservice.share.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class HttpHeaderRequest {

  private String appId;
  private String userId;
  private String merchantId;
}
