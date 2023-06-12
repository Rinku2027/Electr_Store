package com.rinku.electronic.store.ElectronicStore.controller;

import com.rinku.electronic.store.ElectronicStore.dtos.ApiResponseMessage;
import com.rinku.electronic.store.ElectronicStore.dtos.CategoryDto;
import com.rinku.electronic.store.ElectronicStore.dtos.PageableResponse;
import com.rinku.electronic.store.ElectronicStore.dtos.UserDto;
import com.rinku.electronic.store.ElectronicStore.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private Logger logger= LoggerFactory.getLogger(CategoryController.class);
    private CategoryService categoryService;

    @PostMapping("/category")
    public ResponseEntity<CategoryDto> createcategory(@Valid @RequestBody CategoryDto cate) {
        logger.info(" Initiated Request for creating category");
        CategoryDto dto = this.categoryService.create(cate);
        logger.info(" completed Request for creating category");
        return new ResponseEntity<CategoryDto>(dto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDto>> getAllUsers(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "PageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        logger.info("Request  for service layer to get All user ");
        return new ResponseEntity<>(categoryService.getAll(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }


    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updatecategory(@Valid @RequestBody CategoryDto cate,
                                                      @PathVariable String categoryId) {
        logger.info(" Initiated Request for updating category with categoryId :{}", categoryId);
        CategoryDto dto = this.categoryService.updatecategory(cate, categoryId);
        logger.info(" completed Request for updating category with categoryId :{}", categoryId);
        return new ResponseEntity<CategoryDto>(dto, HttpStatus.OK);

    }
    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<CategoryDto> getcategory(@PathVariable String  categoryId) {
        logger.info(" Initiated Request for getting category with categoryId :{}", categoryId);
        CategoryDto dto = this.categoryService.getcategory(categoryId);
        logger.info(" completed Request for getting category with categoryId :{}", categoryId);
        return new ResponseEntity<CategoryDto>(dto, HttpStatus.OK);
    }

    @DeleteMapping("categories/{categoryId}")
    public ResponseEntity<ApiResponseMessage> deletecategory(@PathVariable String  categoryId) {
        logger.info(" Initiated Request for deleting category with categoryId :{}", categoryId);
        this.categoryService.deletecategory(categoryId);
        ApiResponseMessage message = ApiResponseMessage
                .builder()
                .message("User is deleted Sucessfully!!  ")
                .success(true).
                status(HttpStatus.OK)
                .build();
        logger.info(" completing Request for deleting category with categoryId :{}", categoryId);
        return new ResponseEntity<ApiResponseMessage>(message, HttpStatus.OK);
    }
}


