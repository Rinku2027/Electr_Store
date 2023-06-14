package com.rinku.electronic.store.ElectronicStore.service.serviceImpl;

import com.rinku.electronic.store.ElectronicStore.dtos.CategoryDto;
import com.rinku.electronic.store.ElectronicStore.dtos.PageableResponse;
import com.rinku.electronic.store.ElectronicStore.dtos.UserDto;
import com.rinku.electronic.store.ElectronicStore.entity.Category;
import com.rinku.electronic.store.ElectronicStore.entity.User;
import com.rinku.electronic.store.ElectronicStore.exception.ResourceNotFoundException;
import com.rinku.electronic.store.ElectronicStore.helper.Helper;
import com.rinku.electronic.store.ElectronicStore.repository.CategoryRepo;
import com.rinku.electronic.store.ElectronicStore.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper mapper;


    @Override
    public CategoryDto create(CategoryDto categoryDto) {

        String categoryId = UUID.randomUUID().toString();
        categoryDto.setCategoryId(categoryId);
        log.info(" Initiated Request for creating category");
        Category category = mapper.map(categoryDto, Category.class);

        Category save =categoryRepo.save(category);
        log.info(" completed Request for creating category");
        return this.mapper.map(save, CategoryDto.class);
    }
    @Override
    public CategoryDto getcategory(String categoryId) {
        log.info(" Initiated Request for getting category with categoryId :{}", categoryId);
        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        log.info(" completed Request for getting category with categoryId :{}", categoryId);
        return this.mapper.map(category, CategoryDto.class);
    }
    @Override
    public CategoryDto updatecategory(CategoryDto categoryDto, String categoryId) {
        log.info(" Initiated Request for updating category with categoryId :{}", categoryId);
        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());

        Category save = this.categoryRepo.save(category);
        log.info(" completed Request for updating category with categoryId :{}", categoryId);
        return this.mapper.map(save, CategoryDto.class);
    }

    @Override
    public PageableResponse<CategoryDto> getAll(int pageNumber,int pageSize,String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ?
                (Sort.by(sortBy).descending()) :
                (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Category> all = categoryRepo.findAll(pageable);
        Page<Category> page = all;
        PageableResponse<CategoryDto> response = Helper.getPageableResponse(page,CategoryDto.class);
        log.info(" completed Request  for getting users ");
        return response;
    }
    @Override
    public void deletecategory(String categoryId) {

        log.info(" Initiated Request for deleting category with categoryId :{}", categoryId);
        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        log.info(" completed Request for deleting category with categoryId :{}", categoryId);
        this.categoryRepo.delete(category);
    }
}
