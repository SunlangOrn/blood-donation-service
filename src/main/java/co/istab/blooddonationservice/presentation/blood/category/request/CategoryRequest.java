package co.istab.blooddonationservice.presentation.blood.category.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CategoryRequest {

    @NotNull(message = "name is required")
    private String name;
}
