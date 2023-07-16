package com.rinku.electronic.store.ElectronicStore.services;

import com.rinku.electronic.store.ElectronicStore.dtos.CategoryDto;
import com.rinku.electronic.store.ElectronicStore.dtos.PageableResponse;
import com.rinku.electronic.store.ElectronicStore.entity.Category;
import com.rinku.electronic.store.ElectronicStore.repository.CategoryRepo;
import com.rinku.electronic.store.ElectronicStore.service.CategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CategoryServiceImplTest {
    @MockBean
    private CategoryRepo categoryRepo;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper mapper;

    Category category;

    @BeforeEach
    void init() {
        category = Category.builder()
                .title(" this is a sd card related category")
                .description("SD card available for every smart phones on minimum prize")
                .coverImage("abc.png").build();

    }

    @Test
    void createCategory() {

        //Stubbing
        Mockito.when(categoryRepo.save(Mockito.any())).thenReturn(category);
        CategoryDto categoryDto = this.mapper.map(category, CategoryDto.class);
        //Actual method calling
        CategoryDto category1 = categoryService.create(categoryDto);
        //Assertion for validating
        Assertions.assertNotNull(category1);
    }

    @Test
    void updateCategory() {
        String categoryId = "123";
        CategoryDto categoryDto = CategoryDto.builder()
                .coverImage("xyz.png")
                .title(" Birthday pics")
                .description(" some of our 1st Birthday pics ")
                .build();

        //Stubbing
        Mockito.when(categoryRepo.findById(Mockito.anyString())).thenReturn(Optional.of(category));
        Mockito.when(categoryRepo.save(Mockito.any())).thenReturn(category);
        //Actual method calling
        CategoryDto updateCategory = categoryService.updatecategory(categoryDto, categoryId);
        //Assertion for validating
        Assertions.assertEquals(category.getTitle(), updateCategory.getTitle(), " categoryTitle not matched ");
    }

    @Test
    void deleteCategory() {
        String categoryId = "rinku";
        //Stubbing
        Mockito.when(categoryRepo.findById(Mockito.anyString())).thenReturn(Optional.of(category));
        //Actual method calling
        categoryService.deletecategory(categoryId);
        //verifying
        Mockito.verify(categoryRepo, Mockito.times(1)).delete(category);
    }

    @Test
    void getCategoryById() {
        String categoryId = "ms";
        //Stubbing
        Mockito.when(categoryRepo.findById(Mockito.anyString())).thenReturn(Optional.of(category));
        //actual method calling
        CategoryDto category1 = categoryService.getcategory(categoryId);
        //Assertion for Matching
        Assertions.assertNotNull(category1);
        Assertions.assertEquals(category.getDescription(), category1.getDescription(), " description validation failure");
    }

    @Test
    void getAllCategories() {
        Category category1 = Category.builder()
                .coverImage("pqr.png")
                .title("  pics")
                .description(" some of our 1st  pics ")
                .build();

        Category category2 = Category.builder()
                .coverImage("lcwd.png")
                .title("  videos")
                .description(" some of our 1st  videos ")
                .build();

        Category category3 = Category.builder()
                .coverImage("stu.png")
                .title("  family pics")
                .description(" some of our 1st  family pics ")
                .build();


        List<Category> list = Arrays.asList(category1, category2, category3);
        Page<Category> page = new PageImpl<>(list);
        //Stubbing
        Mockito.when(categoryRepo.findAll((Pageable) Mockito.any())).thenReturn(page);
        //actual method calling
        PageableResponse<CategoryDto> allCategories = categoryService.getAll(1, 2, "Title", "desc");
        //assertions checking
        Assertions.assertEquals(3, allCategories.getContent().size(), " test case failed due to not size validate");

    }


}
