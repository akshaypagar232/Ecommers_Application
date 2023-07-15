package com.bikked.controllers;

import com.bikked.dto.CategoryDto;
import com.bikked.dto.PageableResponse;
import com.bikked.dto.ProductDto;
import com.bikked.entity.Category;
import com.bikked.entity.Product;
import com.bikked.service.CategoryService;
import com.bikked.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
class CategoryControllerTest {

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private ProductService productService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    Category category;

    @BeforeEach
    void init() {

        category = Category.builder()
                .coverImage("abc.jpg")
                .title("phone")
                .description("add new phone")
                .build();
    }

    @Test
    void createCategoryTest() throws Exception {

        CategoryDto categoryDto = mapper.map(category, CategoryDto.class);
        Mockito.when(categoryService.createCategory(Mockito.any())).thenReturn(categoryDto);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(category))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").exists());
    }

    private String convertObjectToJsonString(Object category) {
        try {
            return new ObjectMapper().writeValueAsString(category);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Test
    void updateCategoryTest() throws Exception {

        String categoryId = "fdssdf";
        CategoryDto categoryDto = mapper.map(category, CategoryDto.class);
        Mockito.when(categoryService.updateCategory(Mockito.any(), Mockito.anyString())).thenReturn(categoryDto);
        this.mockMvc.perform(MockMvcRequestBuilders.put("/category/" + categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(category))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").exists());
    }

    @Test
    void deleteCategoryTest() {
    }

    @Test
    void getAllCategoryTest() throws Exception {

        Category category1 = Category.builder()
                .coverImage("abc.jpg")
                .title("phone 1")
                .description("add new phone 1")
                .build();
        Category category2 = Category.builder()
                .coverImage("abc.jpg")
                .title("phone 2")
                .description("add new phone 2")
                .build();
        Category category3 = Category.builder()
                .coverImage("abc.jpg")
                .title("phone 3")
                .description("add new phone 3")
                .build();

        CategoryDto categoryDto1 = mapper.map(category1, CategoryDto.class);
        CategoryDto categoryDto2 = mapper.map(category2, CategoryDto.class);
        CategoryDto categoryDto3 = mapper.map(category3, CategoryDto.class);

        PageableResponse<CategoryDto> pageableResponse = new PageableResponse<>();
        pageableResponse.setLastPage(false);
        pageableResponse.setTotalElements(15);
        pageableResponse.setPageSize(3);
        pageableResponse.setPageNumber(0);
        pageableResponse.setContent(Arrays.asList(categoryDto1, categoryDto2, categoryDto3));
        pageableResponse.setTotalPage(10);

        Mockito.when(categoryService.getAllCategory(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pageableResponse);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/category")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound());
    }

    @Test
    void getCategoryByIdTest() throws Exception {

        String categoryId = "djfdj";
        CategoryDto categoryDto = mapper.map(category, CategoryDto.class);
        Mockito.when(categoryService.getCategoryById(Mockito.anyString())).thenReturn(categoryDto);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/category/" + categoryId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound());
    }

    @Test
    void searchCategoryTest() throws Exception {

        String title = "gfghr";
        Category category1 = Category.builder()
                .coverImage("abc.jpg")
                .title("phone 1")
                .description("add new phone 1")
                .build();
        Category category2 = Category.builder()
                .coverImage("abc.jpg")
                .title("phone 2")
                .description("add new phone 2")
                .build();
        Category category3 = Category.builder()
                .coverImage("abc.jpg")
                .title("phone 3")
                .description("add new phone 3")
                .build();

        CategoryDto categoryDto1 = mapper.map(category1, CategoryDto.class);
        CategoryDto categoryDto2 = mapper.map(category2, CategoryDto.class);
        CategoryDto categoryDto3 = mapper.map(category3, CategoryDto.class);

        PageableResponse<CategoryDto> pageableResponse = new PageableResponse<>();
        pageableResponse.setLastPage(false);
        pageableResponse.setTotalElements(15);
        pageableResponse.setPageSize(3);
        pageableResponse.setPageNumber(0);
        pageableResponse.setContent(Arrays.asList(categoryDto1, categoryDto2, categoryDto3));
        pageableResponse.setTotalPage(10);

        Mockito.when(categoryService.searchCategory(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pageableResponse);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/category/search")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound());
    }

    @Test
    void getCategoryByTitleTest() throws Exception {

        String title = "phone";
        CategoryDto categoryDto = mapper.map(category, CategoryDto.class);
        Mockito.when(categoryService.getCategoryByTitle(Mockito.anyString())).thenReturn(categoryDto);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/category/title/" + title)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound());
    }

    @Test
    void uploadCategoryImageTest() {
    }

    @Test
    void serveCategoryImageTest() {
    }

    @Test
    void createWithCategoryTest() throws Exception {

        String categoryId = "fdjf";
        Product product = Product.builder()
                .title("i phone")
                .stock(true)
                .description("add new iphone")
                .price(35000)
                .discountedPrice(15)
                .quantity(21)
                .live(true)
                .imageProduct("abc.jpg")
                .build();
        ProductDto productDto = mapper.map(product, ProductDto.class);
        Mockito.when(productService.createWithCategory(Mockito.any(), Mockito.anyString())).thenReturn(productDto);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/category/categoryId/" + categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(productDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }
}