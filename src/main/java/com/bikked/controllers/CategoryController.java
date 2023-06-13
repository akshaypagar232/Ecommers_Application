package com.bikked.controllers;

import com.bikked.constant.AppConstant;
import com.bikked.dto.CategoryDto;
import com.bikked.dto.PageableResponse;
import com.bikked.exceptions.ApiResponseMessage;
import com.bikked.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController()
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {

        log.info("Initiated request to controller for save the category details");

        CategoryDto category = categoryService.createCategory(categoryDto);

        log.info("Completed request for save the category details");

        return new ResponseEntity<CategoryDto>(category, HttpStatus.CREATED);

    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(
            @Valid @RequestBody CategoryDto categoryDto,
            @PathVariable String categoryId
    ) {

        log.info("Initiated request to controller for update the category details with categoryId : {}", categoryId);

        CategoryDto updateCategory = categoryService.updateCategory(categoryDto, categoryId);

        log.info("Completed request for update the category details with categoryId : {}", categoryId);

        return new ResponseEntity<CategoryDto>(updateCategory, HttpStatus.CREATED);

    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable String categoryId) {

        log.info("Initiated request to controller for delete the category details with categoryId : {}", categoryId);

        categoryService.deleteCategory(categoryId);

        ApiResponseMessage apiResponseMessage = ApiResponseMessage
                .builder()
                .message(AppConstant.Category_Delete)
                .status(true)
                .success(HttpStatus.OK)
                .build();

        log.info("Completed request for delete the category details with categoryId : {}", categoryId);

        return new ResponseEntity<ApiResponseMessage>(apiResponseMessage, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategory(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "2", required = false) int pageize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection

    ) {

        log.info("Initiated request to controller for get all the category details");

        PageableResponse<CategoryDto> allCategory = categoryService.getAllCategory(pageNumber, pageize, sortBy, sortDirection);

        log.info("Completed request for get all the category details");

        return new ResponseEntity<PageableResponse<CategoryDto>>(allCategory,HttpStatus.FOUND);

    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable String categoryId){

        log.info("Initiated request to controller for get category by Id with categoryId : {}",categoryId);

        CategoryDto categoryById = categoryService.getCategoryById(categoryId);

        log.info("Completed request to controller for get category by Id with categoryId : {}",categoryId);

        return new ResponseEntity<CategoryDto>(categoryById,HttpStatus.FOUND);

    }
}
