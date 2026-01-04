package co.istab.blooddonationservice.domain.blood.cateory.application;

import co.istab.blooddonationservice.domain.blood.cateory.entity.Category;
import co.istab.blooddonationservice.domain.blood.cateory.exception.CategoryException;
import co.istab.blooddonationservice.domain.blood.cateory.provider.CategoryDatabaseProvider;
import co.istab.blooddonationservice.domain.blood.cateory.service.CategoryService;
import co.istab.blooddonationservice.domain.file.File;
import co.istab.blooddonationservice.domain.file.FileDatabaseProvider;
import co.istab.blooddonationservice.share.entity.PaginationQuery;
import co.istab.blooddonationservice.share.entity.Paging;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class CategoryServiceFacade implements CategoryService {

    private final CategoryDatabaseProvider provider;
    private final FileDatabaseProvider fileProvider;

    @Override
    public Paging<Category> list(PaginationQuery query) {
        Paging<Category> paging = provider.list(query);
        if(!paging.getItems().isEmpty()) return paging;
        return provider.list(query);
    }

    @Override
    public Category view(Integer id) {
        return provider.getById(id).orElseThrow(CategoryException::notFound);
    }

    @Override
    public Category create(Category category) {

        provider.getByName(category.getName().trim())
                .ifPresent( item -> {throw CategoryException.alreadyExists();});

        category.setName(category.getName());
        category.setCreatedAt(new Date());

        File file = fileProvider.getById(category.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND , "FILE_MEDIA_NOT_FOUND"));
        category.setFileId(file);

        return provider.save(category);
    }

    @Override
    public Category update(Integer id, Category category) {

        Category oldEntity = provider.getById(id).orElseThrow(CategoryException::notFound);
        oldEntity.setName(category.getName());
        oldEntity.setModifiedAt(new Date());

        return provider.save(oldEntity);
    }

    @Override
    public Category delete(Integer id) {

        Category entity = provider.getById(id).orElseThrow(CategoryException::notFound);
        entity.setDeletedAt(new Date());
        return provider.save(entity);
    }

    @Override
    public Paging<Category> listPublic(PaginationQuery query) {
        Paging<Category> paging = provider.list(query);
        if(!paging.getItems().isEmpty()) return paging;
        return provider.list(query);
    }

    @Override
    public Category viewPublic(Integer id) {
        return provider.getById(id).orElseThrow(CategoryException::notFound);
    }
}
