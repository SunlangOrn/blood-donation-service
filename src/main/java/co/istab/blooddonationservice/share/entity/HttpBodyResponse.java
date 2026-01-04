package co.istab.blooddonationservice.share.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HttpBodyResponse<T> {

  @Builder.Default private Integer status = HttpStatus.OK.value();

  @JsonInclude @Builder.Default private String message = "Successful";

  @JsonInclude private T data;

  private HttpBodyPagingResponse paging;

  private HttpBodyErrorResponse error;

  public HttpBodyResponse(
      HttpStatus status, String message, T data, HttpBodyPagingResponse paging) {
    this.status = status.value();
    this.message = message;
    this.data = data;
    this.paging = paging;
  }
}
