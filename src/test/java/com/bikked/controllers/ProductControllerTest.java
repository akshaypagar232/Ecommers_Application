package com.bikked.controllers;

import com.bikked.dto.PageableResponse;
import com.bikked.dto.ProductDto;
import com.bikked.dto.UserDto;
import com.bikked.entity.Product;
import com.bikked.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModelMapper mapper;

    Product product;

    @BeforeEach
    void init() {

        product = Product.builder()
                .title("i phone")
                .stock(true)
                .description("add new iphone")
                .price(35000)
                .discountedPrice(15)
                .quantity(650)
                .live(true)
                .imageProduct("abc.jpg")
                .build();
    }

    @Test
    void createProductTest() throws Exception {

        ProductDto productDto = mapper.map(product, ProductDto.class);
        Mockito.when(productService.createProduct(Mockito.any())).thenReturn(productDto);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(product))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").exists());
    }

    private String convertObjectToJsonString(Object product) {

        try {
            return new ObjectMapper().writeValueAsString(product);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Test
    void updateProductTest() throws Exception {

        String productId = "fghkj";
        ProductDto productDto = mapper.map(product, ProductDto.class);
        Mockito.when(productService.updateProduct(Mockito.any(), Mockito.any())).thenReturn(productDto);
        this.mockMvc.perform(MockMvcRequestBuilders.put("/product/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(productDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").exists());
    }

    @Test
    void deleteProductTest() {
    }

    @Test
    void getAllProductTest() throws Exception {

        Product product1 = Product.builder()
                .title("phone")
                .stock(true)
                .description("add new phone")
                .price(25000)
                .discountedPrice(35)
                .quantity(51)
                .live(true)
                .imageProduct("abc.jpg")
                .build();
        Product product2 = Product.builder()
                .title("i phone")
                .stock(true)
                .description("add new iphone")
                .price(35000)
                .discountedPrice(15)
                .quantity(21)
                .live(true)
                .imageProduct("abc.jpg")
                .build();

        ProductDto productDto1 = mapper.map(product1, ProductDto.class);
        ProductDto productDto2 = mapper.map(product2, ProductDto.class);

        PageableResponse<ProductDto> pageableResponse = new PageableResponse<>();
        pageableResponse.setLastPage(false);
        pageableResponse.setTotalElements(15);
        pageableResponse.setPageSize(2);
        pageableResponse.setPageNumber(0);
        pageableResponse.setContent(Arrays.asList(productDto1, productDto2));
        pageableResponse.setTotalPage(10);

        Mockito.when(productService.getAllProduct(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pageableResponse);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/product/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound());
    }

    @Test
    void getProductLiveTest() throws Exception {

        Product product1 = Product.builder()
                .title("phone")
                .stock(true)
                .description("add new phone")
                .price(25000)
                .discountedPrice(35)
                .quantity(51)
                .live(true)
                .imageProduct("abc.jpg")
                .build();
        Product product2 = Product.builder()
                .title("i phone")
                .stock(true)
                .description("add new iphone")
                .price(35000)
                .discountedPrice(15)
                .quantity(21)
                .live(true)
                .imageProduct("abc.jpg")
                .build();

        ProductDto productDto1 = mapper.map(product1, ProductDto.class);
        ProductDto productDto2 = mapper.map(product2, ProductDto.class);

        PageableResponse<ProductDto> pageableResponse = new PageableResponse<>();
        pageableResponse.setLastPage(false);
        pageableResponse.setTotalElements(100);
        pageableResponse.setPageSize(2);
        pageableResponse.setPageNumber(0);
        pageableResponse.setContent(Arrays.asList(productDto1, productDto2));
        pageableResponse.setTotalPage(10);

        Mockito.when(productService.getAllLive(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pageableResponse);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/product/live")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound());
    }

    @Test
    void getProductByIdTest() throws Exception {

        String productId = "kvflk";
        ProductDto productDto = mapper.map(product, ProductDto.class);
        Mockito.when(productService.getProductById(Mockito.anyString())).thenReturn(productDto);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/product/id/" + productId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound());
    }

    @Test
    void searchProductTest() throws Exception {

        String title = "phone";
        Product product1 = Product.builder()
                .title("phone")
                .stock(true)
                .description("add new phone")
                .price(25000)
                .discountedPrice(35)
                .quantity(51)
                .live(true)
                .imageProduct("abc.jpg")
                .build();
        Product product2 = Product.builder()
                .title("i phone")
                .stock(true)
                .description("add new iphone")
                .price(35000)
                .discountedPrice(15)
                .quantity(21)
                .live(true)
                .imageProduct("abc.jpg")
                .build();

        ProductDto productDto1 = mapper.map(product1, ProductDto.class);
        ProductDto productDto2 = mapper.map(product2, ProductDto.class);

        PageableResponse<ProductDto> pageableResponse = new PageableResponse<>();
        pageableResponse.setLastPage(false);
        pageableResponse.setTotalElements(100);
        pageableResponse.setPageSize(2);
        pageableResponse.setPageNumber(0);
        pageableResponse.setContent(Arrays.asList(productDto1, productDto2));
        pageableResponse.setTotalPage(10);

        Mockito.when(productService.searchProduct(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pageableResponse);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/product/title/" + title)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound());

    }

    @Test
    void uploadProductImageTest() {
    }

    @Test
    void serveUserImageTest() {
    }
}