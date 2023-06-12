package com.bikked.service.impl;

import com.bikked.constant.AppConstant;
import com.bikked.dto.CategoryDto;
import com.bikked.dto.PageableResponse;
import com.bikked.entity.Category;
import com.bikked.exceptions.ResourceNotFoundException;
import com.bikked.helper.Helper;
import com.bikked.repository.CategoryRepository;
import com.bikked.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        log.info("Initiated request for save the Category details in database");

        String categoryId = UUID.randomUUID().toString();

        categoryDto.setCategoryId(categoryId);

        Category category = mapper.map(categoryDto, Category.class);

        Category save = categoryRepository.save(category);

        CategoryDto map = mapper.map(save, CategoryDto.class);

        log.info("Completed request for save the Category details in database");

        return map;
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {

        log.info("Initiated request for update the category details in database with categoryId : {}", categoryId);

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.Category, AppConstant.CategoryId, categoryId));

        Category category1 = mapper.map(categoryDto, Category.class);

        category.setTitle(category1.getTitle());
        category.setDescription(category1.getDescription());
        category.setCoverImage(category1.getCoverImage());

        Category save = categoryRepository.save(category);

        CategoryDto map = mapper.map(save, CategoryDto.class);

        log.info("Completed request for update the Category details in database with categoryId : {}", categoryId);

        return map;
    }

    @Override
    public void deleteCategory(String categoryId) {

        log.info("Initiated request for delete the category details in database with categoryId : {}", categoryId);

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.Category, AppConstant.CategoryId, categoryId));

        categoryRepository.delete(category);

        log.info("Completed request for delete the category details in database with categoryId:{}", categoryId);


    }

    @Override
    public PageableResponse<CategoryDto> getAllCategory(int pageNumber, int pageSize, String sortBy, String sortDirection) {

        Sort sort = (sortDirection.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        log.info("Initiated request for get all Category details in database");

        Page<Category> page = categoryRepository.findAll(pageable);

        log.info("Completed request for get all Category details in database");

        PageableResponse<CategoryDto> response = Helper.getPagableResponse(page, CategoryDto.class);

        return response;
    }

    @Override
    public CategoryDto getCategoryById(String categoryId) {

        log.info("Initiated request for get category By Id category details in database with categoryId:{}", categoryId);

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.Category, AppConstant.CategoryId, categoryId));

        CategoryDto categoryDto = mapper.map(category, CategoryDto.class);

        log.info("Completed request category details in database with categoryId:{}", categoryId);

        return categoryDto;
    }


}
