package co.istab.blooddonationservice.presentation.blood.category.mapper;

import co.istab.blooddonationservice.domain.blood.cateory.entity.Category;
import co.istab.blooddonationservice.presentation.blood.category.request.CategoryRequest;
import co.istab.blooddonationservice.presentation.blood.category.response.CategoryResponse;
import co.istab.blooddonationservice.presentation.blood.category.response.CategoryResponseDetail;
import org.mapstruct.*;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CategoryControllerMapper {

    Category form(CategoryRequest categoryRequest);

    CategoryResponse form(Category category);

    CategoryResponseDetail mapDetail(Category category);
}
