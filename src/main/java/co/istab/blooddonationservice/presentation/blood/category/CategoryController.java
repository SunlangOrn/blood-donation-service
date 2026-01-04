package co.istab.blooddonationservice.presentation.blood.category;

import co.istab.blooddonationservice.domain.blood.cateory.entity.Category;
import co.istab.blooddonationservice.domain.blood.cateory.service.CategoryService;
import co.istab.blooddonationservice.presentation.blood.category.mapper.CategoryControllerMapper;
import co.istab.blooddonationservice.presentation.blood.category.request.CategoryRequest;
import co.istab.blooddonationservice.presentation.blood.category.response.CategoryResponse;
import co.istab.blooddonationservice.presentation.blood.category.response.CategoryResponseDetail;
import co.istab.blooddonationservice.share.entity.HttpBodyPagingResponse;
import co.istab.blooddonationservice.share.entity.HttpBodyResponse;
import co.istab.blooddonationservice.share.entity.PaginationQuery;
import co.istab.blooddonationservice.share.entity.Paging;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static co.istab.blooddonationservice.share.api.ControllerHandler.*;

@RestController
@RequestMapping("/api/v1/admin/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;
    private final CategoryControllerMapper mapper;

    @GetMapping()
    public ResponseEntity<HttpBodyResponse<List<CategoryResponse>>> list(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String keyword
    ){
        Paging<Category> categoryPaging = service.list(PaginationQuery.of(page, size, keyword,null, null, null));
        return responsePaging(
            categoryPaging.getItems().stream().map(mapper::form).toList(),
                HttpBodyPagingResponse.of(
                        categoryPaging.getPage(),
                        categoryPaging.getSize(),
                        categoryPaging.getTotal(),
                        categoryPaging.getTotalPages()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpBodyResponse<CategoryResponseDetail>> view(
            @PathVariable Integer id
    ){
        CategoryResponseDetail response = mapper.mapDetail(service.view(id));
        return responseSucceed(response);
    }

    @PostMapping()
    public ResponseEntity<HttpBodyResponse<CategoryResponseDetail>> create(
            @RequestBody CategoryRequest categoryRequest
    ){
        Category category =mapper.form(categoryRequest);
        CategoryResponseDetail response = mapper.mapDetail(service.create(category));
        return responseSucceed(response);
    }


    @PutMapping("{id}")
    public ResponseEntity<HttpBodyResponse<CategoryResponseDetail>> update(
            @PathVariable Integer id,
            @RequestBody CategoryRequest categoryRequest
    ){
        Category category =mapper.form(categoryRequest);
        CategoryResponseDetail response = mapper.mapDetail(service.update(id, category));
        return responseSucceed(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?>   delete(@PathVariable Integer id){
        service.delete(id);
        return responseDeleted();
    }
    

}
