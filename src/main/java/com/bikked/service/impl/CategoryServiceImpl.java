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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    @Value("${category.image.path}")
    private String imagePath;

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

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.CATEGORY_ID));

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

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.CATEGORY_ID));

        String image = imagePath + category.getCoverImage();

        try {
            Path path = Paths.get(image);
            Files.delete(path);
        } catch (NoSuchFileException ex) {
            log.info("user image not found in folder");
            ex.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        categoryRepository.delete(category);

        log.info("Completed request for delete the category details in database with categoryId:{}", categoryId);
    }

    @Override
    public PageableResponse<CategoryDto> getAllCategory(int pageNumber, int pageSize, String sortBy, String sortDirection) {

        log.info("Initiated request for getAllCategory the category details in database");

        Sort sort = (sortDirection.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Category> page = categoryRepository.findAll(pageable);

        PageableResponse<CategoryDto> response = Helper.getPagableResponse(page, CategoryDto.class);

        log.info("Completed request for getAllCategory the category details in database");

        return response;
    }

    @Override
    public CategoryDto getCategoryById(String categoryId) {

        log.info("Initiated request for get category By Id category details in database with categoryId:{}", categoryId);

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.CATEGORY_ID));

        CategoryDto categoryDto = mapper.map(category, CategoryDto.class);

        log.info("Completed request category details in database with categoryId:{}", categoryId);

        return categoryDto;
    }

    @Override
    public List<CategoryDto> searchCategory(String keyword) {

        log.info("Initiated request for get category by using search keyword in category details in database with keyword:{}", keyword);

        List<Category> list = categoryRepository.findByTitleContaining(keyword);

        List<CategoryDto> categoryDtos = list.stream().map((i) -> mapper.map(i, CategoryDto.class)).collect(Collectors.toList());

        log.info("Completed request for get category by using search keyword in category details in database with keyword:{}", keyword);

        return categoryDtos;
    }

    @Override
    public CategoryDto getCategoryByTitle(String title) {

        log.info("Initiated request for get category by using search title in category details in database with title : {}", title);

        Category category = categoryRepository.findByTitle(title);

        CategoryDto categoryDto = mapper.map(category, CategoryDto.class);

        log.info("Completed request for get category by using search keyword in category details in database with title : {}", title);

        return categoryDto;
    }
}
