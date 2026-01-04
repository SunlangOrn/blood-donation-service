package co.istab.blooddonationservice.domain.blood.cateory.provider;

import co.istab.blooddonationservice.domain.blood.cateory.entity.Category;
import co.istab.blooddonationservice.share.entity.PaginationQuery;
import co.istab.blooddonationservice.share.entity.Paging;

import java.util.Optional;

public interface CategoryDatabaseProvider {

    Paging<Category> list(PaginationQuery paginationQuery);

    Optional<Category> getById(Integer id);

    Optional<Category> getByName(String name);

    Category save(Category category);
}
