package co.istab.blooddonationservice.share.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Builder
public class PageEsEntity<T> {
    private List<T> items;
    private int page;
    private int size;
    private int total;
    private int totalPages;
}
