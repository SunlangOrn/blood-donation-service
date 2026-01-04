package co.istab.blooddonationservice.presentation.blood.category.response;

import co.istab.blooddonationservice.domain.file.File;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponse {

    private String name ;
    private Integer mediaId;
}
