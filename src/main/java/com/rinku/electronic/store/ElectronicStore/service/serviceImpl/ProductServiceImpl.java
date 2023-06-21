package com.rinku.electronic.store.ElectronicStore.service.serviceImpl;

import com.rinku.electronic.store.ElectronicStore.controller.CategoryController;
import com.rinku.electronic.store.ElectronicStore.controller.ProductController;
import com.rinku.electronic.store.ElectronicStore.dtos.CategoryDto;
import com.rinku.electronic.store.ElectronicStore.dtos.PageableResponse;
import com.rinku.electronic.store.ElectronicStore.dtos.ProductDto;
import com.rinku.electronic.store.ElectronicStore.dtos.UserDto;
import com.rinku.electronic.store.ElectronicStore.entity.Category;
import com.rinku.electronic.store.ElectronicStore.entity.Product;
import com.rinku.electronic.store.ElectronicStore.entity.User;
import com.rinku.electronic.store.ElectronicStore.exception.ResourceNotFoundException;
import com.rinku.electronic.store.ElectronicStore.helper.ApiConstants;
import com.rinku.electronic.store.ElectronicStore.helper.Helper;
import com.rinku.electronic.store.ElectronicStore.repository.ProductRepo;
import com.rinku.electronic.store.ElectronicStore.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
@Autowired
private ProductRepo productRepo;
@Autowired
private ModelMapper mapper;
    @Override
    public ProductDto create(ProductDto productDto) {
        Product product = mapper.map(productDto, Product.class);
        //product id
        String productId = UUID.randomUUID().toString();
        product.setProductId(productId);
        //added
        product.setAddedDate(new Date());
        Product saveProduct = productRepo.save(product);
        return mapper.map(saveProduct,ProductDto.class);
    }
    @Override
    public ProductDto update(ProductDto productDto, String productId) {
        log.info(" Initiated Request  for updating user with prodId :{}", productId);
        Product product = this.productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(ApiConstants.EXCEPTION_MESSAGE, productId, productId));
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setQuantity(productDto.getQuantity());
        product.setLive(productDto.isLive());
        product.setStock(productDto.isStock());

        Product updatedproduct = productRepo.save(product);
        log.info(" Completed Request  for updating user with productId :{}", productId);
        return mapper.map(updatedproduct,ProductDto.class);
    }

    @Override
    public void delete(String productId) {
        Product product = this.productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(ApiConstants.EXCEPTION_MESSAGE, productId, productId));
        productRepo.delete(product);
    }

    @Override
    public ProductDto get(String productId) {
        Product product = this.productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(ApiConstants.EXCEPTION_MESSAGE, productId, productId));
    return mapper.map(product,ProductDto.class);

    }

    @Override

    public PageableResponse<ProductDto> getAll(int pageNumber,int pageSize,String sortBy,String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ?
                (Sort.by(sortBy).descending()) :
                (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Product> all = productRepo.findAll(pageable);
        Page<Product> page = all;
       return Helper.getPageableResponse(page,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllLive(int pageNumber,int pageSize,String sortBy,String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ?
                (Sort.by(sortBy).descending()) :
                (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Product> all = productRepo.findByLiveTrue(pageable);
        Page<Product> page = all;
        return Helper.getPageableResponse(page,ProductDto.class);
    }
    @Override
    public PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber,int pageSize,String sortBy,String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ?
                (Sort.by(sortBy).descending()) :
                (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Product> all = productRepo.findByTitleContaining(subTitle,pageable);
        Page<Product> page = all;
        return Helper.getPageableResponse(page,ProductDto.class);
    }
}
