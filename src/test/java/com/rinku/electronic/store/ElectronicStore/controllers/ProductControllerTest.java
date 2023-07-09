package com.rinku.electronic.store.ElectronicStore.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rinku.electronic.store.ElectronicStore.dtos.PageableResponse;
import com.rinku.electronic.store.ElectronicStore.dtos.ProductDto;
import com.rinku.electronic.store.ElectronicStore.entity.Product;
import com.rinku.electronic.store.ElectronicStore.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProductControllerTest {

    @MockBean
    private ProductService productServiceI;

    @Autowired
    private ModelMapper mapper;


    @Autowired
    private MockMvc mockMvc;

    Product product;
    @BeforeEach
    void init (){
        product = Product.builder()
                .title(" Mobile phones ")
                .description(" Mobile phones above 1 lac with white background ")
                .live(true)
                .productImageName(" mobile.png")
                .discountedPrice(85000)
                .price(95000)
                .quantity(210)
                .stock(true).build();

    }
    @Test
    void createProductTest() throws Exception {
        ProductDto productDto = this.mapper.map(product, ProductDto.class);

        Mockito.when(productServiceI.create(Mockito.any())).thenReturn(productDto);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/product")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(product))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.weight").exists());

    }

    private String convertObjectToJsonString(Object product) {

        try {
            return new ObjectMapper().writeValueAsString(product);
        }catch(Exception e ){
            e.printStackTrace();
            return  null;
        }
    }

    @Test
    void updateProductTest() throws Exception {
        String productId ="1234";

        ProductDto productdto = ProductDto.builder()
                .title(" Mobile phones ")
                .description(" Mobile phones above 1 lac with blue background ")
                .live(true)
                .productImageName(" phone.png")
                .discountedPrice(75000)
                .price(95000)
                .stock(false).build();


        Mockito.when(productServiceI.update(Mockito.any(),Mockito.anyString())).thenReturn(productdto);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/product/"+productId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(product))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.weight").exists());


    }

    @Test
    void getProductByIdTest() throws Exception {

        String productId ="klm";
        ProductDto productDto = this.mapper.map(product, ProductDto.class);
        Mockito.when(productServiceI.get(Mockito.anyString())).thenReturn(productDto);
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/product/"+productId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.colour").exists());


    }

    @Test
    void deleteProductTest() {
    }

    @Test
    void getAllProductsTest() throws Exception {

        ProductDto product1 = ProductDto.builder()
                .title(" cricket kit  ")
                .description(" cricket kits above 1 lac  ")
                .live(true)
                .productImageName(" bat.png")
                .discountedPrice(65000)
                .price(75000)
                .stock(false).build();

        ProductDto product2 = ProductDto.builder()
                .title(" Mobile phones ")
                .description(" Mobile phones above 1 lac with blue background ")
                .live(true)
                .productImageName(" phone.png")
                .discountedPrice(75000)
                .price(95000)
                .stock(false).build();

        ProductDto product3 = ProductDto.builder()
                .title(" table tennis  ")
                .description(" table tennis kit below 2 lac  ")
                .live(true)
                .productImageName(" tennis.png")
                .discountedPrice(45000)
                .price(55000)
                .stock(false).build();

        PageableResponse<ProductDto> pageableResponse = new PageableResponse<>();
        pageableResponse.setLastPage(false);
        pageableResponse.setTotalElements(2000);
        pageableResponse.setPageNumber(50);
        pageableResponse.setContent(Arrays.asList(product1,product2,product3));
        pageableResponse.setTotalPages(200);
        pageableResponse.setPageSize(20);

        Mockito.when(productServiceI.getAll(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageableResponse);

        //request for url
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getAllLiveProductsTest() throws Exception {

        ProductDto product2 = ProductDto.builder()
                .title(" Mobile phones ")
                .description(" Mobile phones above 1 lac with blue background ")
                .live(true)
                .productImageName(" phone.png")
                .discountedPrice(75000)
                .price(95000)
                .stock(false).build();

        ProductDto product3 = ProductDto.builder()
                .title(" table tennis  ")
                .description(" table tennis kit below 2 lac  ")
                .live(true)
                .productImageName(" tennis.png")
                .discountedPrice(45000)
                .price(55000)
                .stock(false).build();

        PageableResponse<ProductDto> pageableResponse = new PageableResponse<>();
        pageableResponse.setLastPage(false);
        pageableResponse.setTotalElements(2000);
        pageableResponse.setPageNumber(50);
        pageableResponse.setContent(Arrays.asList(product2,product3));
        pageableResponse.setTotalPages(200);
        pageableResponse.setPageSize(20);

        Mockito.when(productServiceI.getAllLive(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageableResponse);

        //request for url
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/products/live")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void searchProductByTitleTest() throws Exception {

        String subtitle = "kit";

        ProductDto product1 = ProductDto.builder()
                .title(" cricket kit  ")
                .description(" cricket kits above 1 lac  ")
                .live(true)
                .productImageName(" bat.png")
                .discountedPrice(55000)
                .price(25000)
                .stock(false).build();

        ProductDto product2 = ProductDto.builder()
                .title(" Mobile phones ")
                .description(" Mobile phones above 1 lac with blue background ")
                .live(true)
                .productImageName(" phone.png")
                .discountedPrice(75000)
                .price(95000)
                .stock(false).build();

        PageableResponse<ProductDto> pageableResponse = new PageableResponse<>();
        pageableResponse.setLastPage(false);
        pageableResponse.setTotalElements(2000);
        pageableResponse.setPageNumber(50);
        pageableResponse.setContent(Arrays.asList(product1,product2));
        pageableResponse.setTotalPages(200);
        pageableResponse.setPageSize(20);

        Mockito.when(productServiceI.searchByTitle(Mockito.anyString(),Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageableResponse);

        //request for url
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/product/search/"+subtitle)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound());
    }


}
