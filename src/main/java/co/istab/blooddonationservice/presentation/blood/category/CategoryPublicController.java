package co.istab.blooddonationservice.presentation.blood.category;

import co.istab.blooddonationservice.domain.blood.cateory.entity.Category;
import co.istab.blooddonationservice.domain.blood.cateory.service.CategoryService;
import co.istab.blooddonationservice.presentation.blood.category.mapper.CategoryControllerMapper;
import co.istab.blooddonationservice.presentation.blood.category.response.CategoryResponse;
import co.istab.blooddonationservice.share.entity.HttpBodyPagingResponse;
import co.istab.blooddonationservice.share.entity.HttpBodyResponse;
import co.istab.blooddonationservice.share.entity.PaginationQuery;
import co.istab.blooddonationservice.share.entity.Paging;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static co.istab.blooddonationservice.share.api.ControllerHandler.responsePaging;
import static co.istab.blooddonationservice.share.api.ControllerHandler.responseSucceed;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryPublicController {

    private final CategoryService service;
    private final CategoryControllerMapper mapper;

    @GetMapping()
    public ResponseEntity<HttpBodyResponse<List<CategoryResponse>>> list(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String keyword
    ){
        Paging<Category> categoryPaging = service.list(PaginationQuery.of(page, size, keyword,null, null, null,  null));
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
    public ResponseEntity<HttpBodyResponse<CategoryResponse>> viewPublic(@PathVariable Integer id){
        CategoryResponse response = mapper.form(service.viewPublic(id));
        return responseSucceed(response);
    }
}
