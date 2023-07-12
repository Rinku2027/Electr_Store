
package com.rinku.electronic.store.ElectronicStore.services;

import com.rinku.electronic.store.ElectronicStore.dtos.PageableResponse;
import com.rinku.electronic.store.ElectronicStore.dtos.ProductDto;
import com.rinku.electronic.store.ElectronicStore.entity.Category;
import com.rinku.electronic.store.ElectronicStore.entity.Product;
import com.rinku.electronic.store.ElectronicStore.repository.CategoryRepo;
import com.rinku.electronic.store.ElectronicStore.repository.ProductRepo;
import com.rinku.electronic.store.ElectronicStore.service.ProductService;
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

public class ProductServiceTest {

    @MockBean
    private ProductRepo productRepo;

    @MockBean
    private CategoryRepo categoryRepo;

    @Autowired
    private ProductService productServiceI;

    @Autowired
    private ModelMapper mapper;

    Product product;

    @BeforeEach
    void setUp() {
        product = Product.builder()
                .title(" Mobile phones ")
                .description(" Mobile phones above 1 lac with blue background ")
                .live(true)
                .productImageName(" mobile.png")
                .discountedPrice(85000)
                .price(95000)
                .stock(true).build();
    }

    @Test
    void createProduct() {
        ProductDto productdto = this.mapper.map(product, ProductDto.class);

        //Stubbing
        Mockito.when(productRepo.save(Mockito.any())).thenReturn(product);
        //Actual Method calling
        ProductDto product1 = productServiceI.create(productdto);
        //Assertion
        Assertions.assertNotNull(product1);
        Assertions.assertEquals(product.getPrice(), product1.getPrice(), "price not matched with expected");

    }

    @Test
    void updateProduct() {
        ProductDto productDto = ProductDto.builder()
                .title(" Mobile phones ")
                .description(" Mobile phones above 1 lac with blue background ")
                .live(true)
                .productImageName(" mobile1.png")
                .discountedPrice(85000)
                .price(95000)
                .stock(true).build();

        String productId = "abcd";
        //Stubbing
        Mockito.when(productRepo.findById(Mockito.anyString())).thenReturn(Optional.of(product));
        Mockito.when(productRepo.save(Mockito.any())).thenReturn(product);
        //Actual Method calling
        ProductDto productDto1 = productServiceI.update(productDto, productId);
        //Assertion
        Assertions.assertEquals(productDto1.getPrice(), product.getPrice(), " test case failed !!");
        Assertions.assertNotNull(productDto1);
    }

    @Test
    void getProductById() {

        String productId = "abcds";
        ProductDto productdto = this.mapper.map(product, ProductDto.class);

        //Stubbing
        Mockito.when(productRepo.findById(Mockito.anyString())).thenReturn(Optional.of(product));
        //Actual Method calling
        ProductDto product1 = productServiceI.get(productId);
        //Assertion
        Assertions.assertNotNull(product);
        Assertions.assertEquals("85000", product.getPrice(), " test case failed !!");
    }

    @Test
    void getAllProducts() {
        Product product1 = Product.builder()
                .title(" Mobile phones ")
                .description(" Mobile phones  ")
                .live(true)
                .productImageName(" mobile.png")
                .discountedPrice(85000)
                .price(95000)
                .stock(true).build();
        Product product2 = Product.builder()
                .title(" Mobile phones ")
                .description(" Mobile phones")
                .live(true)
                .productImageName(" mobile.png")
                .discountedPrice(85000)
                .price(95000)
                .stock(true).build();

        Product product3 = Product.builder()
                .title(" Mobile phones ")
                .description(" Mobile phones above 1 lac with blue background ")
                .live(true)
                .productImageName(" mobile.png")
                .discountedPrice(85000)
                .price(95000)
                .stock(true).build();

        Product product4 = Product.builder()
                .title(" Mobile phones ")
                .description(" Mobile phones above 1 lac with blue background ")
                .live(false)
                .productImageName(" mobile.png")
                .discountedPrice(85000)
                .price(95000)
                .stock(true).build();

        List<Product> list = Arrays.asList(product1, product2, product3, product4);

        Page<Product> page = new PageImpl<>(list);
        //Stubbing
          Mockito.when(productRepo.findAll((Pageable) Mockito.any())).thenReturn(page);
        //Actual Method calling
        PageableResponse<ProductDto> response = productServiceI.getAll(1, 2, "colour", "asc");
        //Assertion
        Assertions.assertEquals(4, response.getContent().size(), " test case failed ");

    }

    @Test
    void getAllLiveProducts() {

        Product product1 = Product.builder()
                .title(" Mobile phones ")
                .description(" Mobile phones above 1 lac with blue background ")
                .live(true)
                .productImageName(" mobile.png")
                .discountedPrice(85000)
                .price(95000)
                .stock(true).build();
        Product product2 = Product.builder()
                .title(" Mobile phones ")
                .description(" Mobile phones above 1 lac with blue background ")
                .live(true)
                .productImageName(" mobile.png")
                .discountedPrice(85000)
                .price(95000)
                .stock(true).build();

        Product product3 = Product.builder()
                .title(" Mobile phones ")
                .description(" Mobile phones ")
                .live(true)
                .productImageName(" mobile.png")
                .discountedPrice(85000)
                .price(95000)
                .stock(true).build();

        List<Product> list = Arrays.asList(product1, product2, product3);
        Page<Product> page = new PageImpl<>(list);
        //Stubbing
        Mockito.when(productRepo.findByLiveTrue((Pageable) Mockito.any())).thenReturn(page);
        //Actual Method calling
        PageableResponse<ProductDto> response = productServiceI.getAllLive(0, 1, "price", "asc");
        //Assertion
        Assertions.assertEquals(3, response.getContent().size(), " test case failed ");
    }

    @Test
    void deleteProduct() {
        String productId = "xyz";
        //Stubbing
        Mockito.when(productRepo.findById(Mockito.anyString())).thenReturn(Optional.of(product));
        //Actual Method calling
        productServiceI.delete(productId);
        //Assertion
        Mockito.verify(productRepo, Mockito.times(1)).delete(product);
    }

    @Test
    void searchProductByTitle() {

        String keyword = "azs";
        Product product1 = Product.builder()
                .title(" Mobile phones ")
                .description(" Mobile phones above 1 lac with blue background ")
                .live(true)
                .productImageName(" mobile.png")
                .discountedPrice(85000)
                .price(95000)
                .stock(true).build();
        Product product2 = Product.builder()
                .title(" Mobile phones ")
                .description(" Mobile phones above 1 lac with blue background ")
                .live(true)
                .productImageName(" mobile.png")
                .discountedPrice(85000)
                .price(95000)
                .stock(true).build();

        List<Product> list = Arrays.asList(product1, product2);
        Page<Product> page = new PageImpl<>(list);
        //Stubbing
        Mockito.when(productRepo.findByTitleContaining(Mockito.anyString(), Mockito.any())).thenReturn(page);
        //Actual Method calling
        PageableResponse<ProductDto> response = productServiceI.searchByTitle(keyword, 0, 1, "price", "asc");
        //Assertion
        Assertions.assertEquals(2, response.getContent().size(), " test case failed ");


    }
/*
    @Test
    void createProductWithCategory() {

        String categoryId = "av";
        Category category = Category.builder()
                .title(" this is a sd card related category")
                .description("SD card available for every smart phones on minimum prize")
                .coverImage("abc.png").build();
        //Stubbing
        Mockito.when(categoryRepo.findById(Mockito.anyString())).thenReturn(Optional.of(category));
        Mockito.when(productRepo.save(Mockito.any())).thenReturn(product);

        ProductDto productdto = this.mapper.map(product, ProductDto.class);
        //Actual Method calling
        productServiceI.create(productdto, categoryId);
        //Assertion
        Assertions.assertEquals(product.getDiscountedPrice(), productdto.getDiscountedPrice(), " test case failed due to inEquality in validation");
    }
*/

}
