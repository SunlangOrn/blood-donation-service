package co.istab.blooddonationservice.domain.blood.cateory.service;

import co.istab.blooddonationservice.domain.blood.cateory.entity.Category;
import co.istab.blooddonationservice.share.entity.PaginationQuery;
import co.istab.blooddonationservice.share.entity.Paging;

public interface CategoryService {

    Paging<Category> list(PaginationQuery query);

    Category view(Integer id);

    Category create(Category category);

    Category update(Integer id, Category category);

    Category delete(Integer id);

    Paging<Category> listPublic(PaginationQuery query);

    Category viewPublic(Integer id);
}
