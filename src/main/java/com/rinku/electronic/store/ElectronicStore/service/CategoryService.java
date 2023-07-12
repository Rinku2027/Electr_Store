package com.rinku.electronic.store.ElectronicStore.service;

import com.rinku.electronic.store.ElectronicStore.dtos.CategoryDto;
import com.rinku.electronic.store.ElectronicStore.dtos.PageableResponse;
import java.util.List;

public interface CategoryService {

    CategoryDto create(CategoryDto categoryDto);

    CategoryDto getcategory(String categoryId);

    CategoryDto updatecategory(CategoryDto categoryDto, String categoryId);

    PageableResponse<CategoryDto> getAll(int pageNumber,int pageSize,String sortBy, String sortDir);

    void deletecategory(String categoryId);

}
