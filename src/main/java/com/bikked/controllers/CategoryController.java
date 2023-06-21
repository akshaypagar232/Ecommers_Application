package com.bikked.controllers;

import com.bikked.constant.AppConstant;
import com.bikked.dto.CategoryDto;
import com.bikked.dto.ImageResponse;
import com.bikked.dto.PageableResponse;
import com.bikked.dto.UserDto;
import com.bikked.exceptions.ApiResponseMessage;
import com.bikked.service.CategoryService;
import com.bikked.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@RestController()
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private FileService fileService;

    @Value("${category.image.path}")
    private String imageUploadPath;

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
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageize,
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

    @GetMapping
    public ResponseEntity<List<CategoryDto>> searchCategory(@RequestParam String title) {

        log.info("Initiated request pass service for get category details with title : {}", title);

        List<CategoryDto> categoryDtos = categoryService.searchCategory(title);

        log.info("Completed request for get category details with title : {}", title);

        return new ResponseEntity<List<CategoryDto>>(categoryDtos, HttpStatus.FOUND);
    }

    @GetMapping("/{title}")
    public ResponseEntity<CategoryDto> getCategoryByTitle(@PathVariable String title) {

        log.info("Initiated request pass service for get category details with title : {}", title);

        CategoryDto categoryDto = categoryService.getCategoryByTitle(title);

        log.info("Completed request for get category details with title : {}", title);

        return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.FOUND);
    }

    @PostMapping("/image/{categoryId}")
    public ResponseEntity<ImageResponse> uploadCategoryImage(
            @RequestParam("categoryImage") MultipartFile image,
            @PathVariable String categoryId

    ) throws IOException {

        log.info("Initiated request pass service for upload category image details with categoryId : {}", categoryId);

        String imageName = fileService.uploadFile(image, imageUploadPath);

        CategoryDto categoryDto = categoryService.getCategoryById(categoryId);

        categoryDto.setCoverImage(imageName);

        CategoryDto updateCategory = categoryService.updateCategory(categoryDto, categoryId);

        ImageResponse imageResponse = ImageResponse
                .builder()
                .imageName(imageName)
                .success(HttpStatus.CREATED)
                .status(true)
                .build();

        log.info("Completed request for upload category image details with categoryId : {}", categoryId);

        return new ResponseEntity<ImageResponse>(imageResponse, HttpStatus.CREATED);
    }

    @GetMapping("/image/{categoryId}")
    public void serveCategoryImage(
            @PathVariable String categoryId,
            HttpServletResponse response

    ) throws IOException {

        log.info("Initiated request pass service for serve category image details with categoryId : {}", categoryId);

        CategoryDto categoryDto = categoryService.getCategoryById(categoryId);

        InputStream resource = fileService.getResource(imageUploadPath, categoryDto.getCoverImage());

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        StreamUtils.copy(resource, response.getOutputStream());

        log.info("Completed request for serve category image details with categoryId : {}", categoryId);

    }

}
