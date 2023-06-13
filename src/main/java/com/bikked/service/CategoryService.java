package com.bikked.service;

import com.bikked.dto.CategoryDto;
import com.bikked.dto.PageableResponse;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto, String categoryIg);

    void deleteCategory(String categoryId);

    PageableResponse<CategoryDto> getAllCategory(int pageNumber, int pageSize, String sortBy, String sortDirection);

    CategoryDto getCategoryById(String categoryId);


}
