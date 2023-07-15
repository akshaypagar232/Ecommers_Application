package com.bikked.service.impl;

import com.bikked.dto.CategoryDto;
import com.bikked.dto.PageableResponse;
import com.bikked.entity.Category;
import com.bikked.repository.CategoryRepository;
import com.bikked.service.CategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryServiceImplTest {

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CategoryService categoryService;

    Category category;

    @BeforeEach
    public void init() {

        category  = Category.builder()
                .title("phone")
                .description("new phones")
                .coverImage("abc.jpg")
                .build();
    }

    @Test
    void createCategoryTest() {

        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);
        CategoryDto categoryDto = mapper.map(category, CategoryDto.class);
        CategoryDto category1 = categoryService.createCategory(categoryDto);
        Assertions.assertEquals(category1.getTitle(),categoryDto.getTitle());
    }

    @Test
    void updateCategoryTest() {

        String categoryId = "ca1";
        Mockito.when(categoryRepository.findById(Mockito.anyString())).thenReturn(Optional.of(category));
        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);
        CategoryDto categoryDto = mapper.map(category, CategoryDto.class);
        CategoryDto updateCategory = categoryService.updateCategory(categoryDto, categoryId);
        Assertions.assertEquals(updateCategory.getTitle(),categoryDto.getTitle());
    }

    @Test
    void deleteCategoryTest() {

        Mockito.when(categoryRepository.findById(Mockito.anyString())).thenReturn(Optional.of(category));
        categoryService.deleteCategory("phone");
        Mockito.verify(categoryRepository,Mockito.times(1)).delete(category);
    }

    @Test
    void getAllCategoryTest() {

        Category category1 = Category.builder()
                .title("phone")
                .description("new phones")
                .coverImage("abc.jpg")
                .build();
        Category category2 = Category.builder()
                .title("phone")
                .description("new phones")
                .coverImage("abc.jpg")
                .build();
        List<Category> categoryList = Arrays.asList(category1,category2);
        Page<Category> page =new PageImpl<>(categoryList);
        Mockito.when(categoryRepository.findAll((Pageable) Mockito.any())).thenReturn(page);
        PageableResponse<CategoryDto> allCategory = categoryService.getAllCategory(0, 4, "title", "asc");
        Assertions.assertEquals(2,allCategory.getContent().size());
    }

    @Test
    void getCategoryByIdTest() {

        String categoryId = "a1";
        Mockito.when(categoryRepository.findById(Mockito.anyString())).thenReturn(Optional.of(category));
        CategoryDto categoryDto1 = mapper.map(category, CategoryDto.class);
        CategoryDto categoryDto = categoryService.getCategoryById(categoryId);
        Assertions.assertEquals(categoryDto.getTitle(),categoryDto1.getTitle());
    }

    @Test
    void searchCategoryTest() {

        String keyword ="oppo";
        Category category1 = Category.builder()
                .title("oppo23")
                .description("new oppo 23")
                .coverImage("abc.jpg")
                .build();
        Category category2 = Category.builder()
                .title("vivo")
                .description("new vivo 54")
                .coverImage("abc.jpg")
                .build();
        List<Category> categoryList = Arrays.asList(category1,category2);
        Page<Category> page =new PageImpl<>(categoryList);
        Mockito.when(categoryRepository.findByTitleContaining(Mockito.anyString(),(Pageable) Mockito.any())).thenReturn(page);
        PageableResponse<CategoryDto> allCategory = categoryService.searchCategory(keyword,0,4,"title","asc");
        Assertions.assertEquals(2,allCategory.getContent().size());
    }

    @Test
    void getCategoryByTitleTest() {

        Mockito.when(categoryRepository.findByTitle(Mockito.anyString())).thenReturn(Optional.of(category));
        CategoryDto categoryDto1 = mapper.map(category, CategoryDto.class);
        CategoryDto categoryDto = categoryService.getCategoryByTitle(category.getTitle());
        Assertions.assertEquals(categoryDto.getTitle(),categoryDto1.getTitle());
    }
}