package co.istab.blooddonationservice.share.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Builder
public class PaginationQuery {
  private Integer page;
  private Integer size;
  private String keyword;
  private String typeBlood;
  private String status;
  private String location;
  private String sortBy;

    public static PaginationQuery of(Integer page, Integer size, String keyword, String typeBlood, String status, String location) {
        return null;
    }

    public int getPage() {
    return Objects.nonNull(this.page) ? this.page : 1;
  }

  public int getSize() {
    return Objects.nonNull(this.size) ? this.size : 10;
  }
}
