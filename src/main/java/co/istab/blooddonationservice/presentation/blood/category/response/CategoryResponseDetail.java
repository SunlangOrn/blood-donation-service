package co.istab.blooddonationservice.presentation.blood.category.response;

import co.istab.blooddonationservice.domain.file.File;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponseDetail {

    private Integer id;
    private String name;
    private Integer mediaId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date createdAt;

}
