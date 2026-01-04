package co.istab.blooddonationservice.infrastructure.database.provider;

import co.istab.blooddonationservice.domain.blood.cateory.entity.Category;
import co.istab.blooddonationservice.domain.blood.cateory.provider.CategoryDatabaseProvider;
import co.istab.blooddonationservice.infrastructure.database.mapper.CategoryDatabaseMapper;
import co.istab.blooddonationservice.infrastructure.database.mysql.Entity.CategoryEntity;
import co.istab.blooddonationservice.infrastructure.database.mysql.repository.CategoryJpaRepository;
import co.istab.blooddonationservice.share.entity.PaginationQuery;
import co.istab.blooddonationservice.share.entity.Paging;
import co.istab.blooddonationservice.share.utility.PageNumberUtility;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryDatabaseProviderFacade implements CategoryDatabaseProvider {

    private final CategoryJpaRepository repository;
    private final CategoryDatabaseMapper mapper;

    @Override
    public Paging<Category> list(PaginationQuery paginationQuery) {
        Page<CategoryEntity> entityPage =
                repository.findAll(
                        (root, querySpec, criteriaBuilder) -> {
                            Predicate notDeleted = criteriaBuilder.isNull(root.get("deletedAt"));
                            if (paginationQuery.getKeyword() != null && !paginationQuery.getKeyword().trim().isEmpty()) {
                                Predicate keywordMatch = criteriaBuilder.like(root.get("name"), "%" + paginationQuery.getKeyword() + "%");
                                return criteriaBuilder.and(notDeleted, keywordMatch);
                            }
                            return notDeleted;
                        },
                        PageRequest.of(
                                PageNumberUtility.in(paginationQuery.getPage()),
                                100,
                                Sort.by(Sort.Direction.DESC, "id")));
        return Paging.<Category>builder()
                .items(entityPage.stream().map(mapper::from).toList())
                .page(PageNumberUtility.out(entityPage.getNumber()))
                .size(entityPage.getSize())
                .totalPages(entityPage.getTotalPages())
                .total(entityPage.getNumberOfElements())
                .build();
    }

    @Override
    public Optional<Category> getById(Integer id) {
        return repository.findOne((root, query, criteriaBuilder) ->
                        criteriaBuilder.and(criteriaBuilder.isNull(root.get("deletedAt")),
                                criteriaBuilder.equal(root.get("id"), id)))
                .map(mapper::from);
    }

    @Override
    public Optional<Category> getByName(String name) {
        return repository.findOne((root, query, criteriaBuilder) ->
                        criteriaBuilder.and(criteriaBuilder.isNull(root.get("DeletedAt")),
                                criteriaBuilder.equal(root.get("name"), name)))
                .map(mapper::from);
    }

    @Override
    public Category save(Category category) {
        CategoryEntity categoryEntity = mapper.from(category);
        return mapper.from(repository.save(categoryEntity));
    }
}
