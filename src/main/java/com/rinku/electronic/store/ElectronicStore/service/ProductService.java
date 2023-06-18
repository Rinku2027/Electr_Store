package com.rinku.electronic.store.ElectronicStore.service;

import com.rinku.electronic.store.ElectronicStore.dtos.ProductDto;

import java.util.List;

public interface ProductService {

    ProductDto create(ProductDto productDto);

    ProductDto update(ProductDto productDto, String productId);

    void delete(String productId);
    ProductDto get(String productId);
    List<ProductDto> getAll();
    List<ProductDto> getAllLive();

    List<ProductDto> searchByTitle(String subTitle);



}
