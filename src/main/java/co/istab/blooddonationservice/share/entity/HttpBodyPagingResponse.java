package co.istab.blooddonationservice.share.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Optional;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Builder
@Data
public class HttpBodyPagingResponse {

  public static final String SIZE = "size";
  public static final String PAGE = "page";
  public static final String TOTAL = "total";
  public static final String TOTAL_PAGES = "totalPages";

  private int page;
  private int size;
  private long total;
  private int totalPages;

  public static HttpBodyPagingResponse from(Map<String, Object> parameters) {

    if (Optional.ofNullable(parameters).map(Map::isEmpty).orElse(Boolean.TRUE)) {
      return null;
    }

    HttpBodyPagingResponse paging = new HttpBodyPagingResponse();

    paging.setSize((Integer) parameters.getOrDefault(HttpBodyPagingResponse.SIZE, 0));
    paging.setTotal((Integer) parameters.getOrDefault(HttpBodyPagingResponse.TOTAL, 0));
    paging.setPage((Integer) parameters.getOrDefault(HttpBodyPagingResponse.PAGE, 0));
    paging.setTotalPages((Integer) parameters.getOrDefault(HttpBodyPagingResponse.TOTAL_PAGES, 0));

    return paging;
  }
}
