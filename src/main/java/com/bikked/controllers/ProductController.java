package com.bikked.controllers;

import com.bikked.constant.AppConstant;
import com.bikked.dto.ImageResponse;
import com.bikked.dto.PageableResponse;
import com.bikked.dto.ProductDto;
import com.bikked.dto.UserDto;
import com.bikked.exceptions.ApiResponseMessage;
import com.bikked.service.FileService;
import com.bikked.service.ProductService;
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
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private FileService fileService;

    @Value("${product.image.path}")
    private String imagePath;

    @PostMapping
    ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {

        log.info("Initiated request for save the product details");
        ProductDto product = productService.createProduct(productDto);
        log.info("Completed request for save the product details");

        return new ResponseEntity<ProductDto>(product, HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody ProductDto productDto, @PathVariable String productId) {

        log.info("Initiated request for update the product details with productId : {}", productId);
        ProductDto product = productService.updateProduct(productDto, productId);
        log.info("Completed request for update the product details with productId : {}", productId);

        return new ResponseEntity<ProductDto>(product, HttpStatus.CREATED);
    }

    @DeleteMapping("/{productId}")
    ResponseEntity<ApiResponseMessage> deleteProduct(@PathVariable String productId) {

        log.info("Initiated request for delete the product details with productId : {}", productId);
        productService.deleteProduct(productId);
        ApiResponseMessage message = ApiResponseMessage.builder()
                .message(AppConstant.PRODUCT_ID)
                .status(true)
                .success(HttpStatus.OK)
                .build();
        log.info("Completed request for delete the product details with productId : {}", productId);

        return new ResponseEntity<ApiResponseMessage>(message, HttpStatus.OK);
    }

    @GetMapping("/all")
    ResponseEntity<PageableResponse> getAllProduct(
            @RequestParam(value = "pageNumber", defaultValue = "1", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "2", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection
    ) {
        log.info("Initiated request for getAllProduct the product details");
        PageableResponse<ProductDto> allProduct = productService.getAllProduct(pageNumber, pageSize, sortBy, sortDirection);
        log.info("Completed request for getAllProduct the product details");

        return new ResponseEntity<PageableResponse>(allProduct, HttpStatus.FOUND);
    }

    @GetMapping("/live")
    ResponseEntity<PageableResponse> getProductLive(
            @RequestParam(value = "pageNumber", defaultValue = "1", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "2", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection
    ) {
        log.info("Initiated request for getProductLive the product details");
        PageableResponse<ProductDto> allLive = productService.getAllLive(pageNumber, pageSize, sortBy, sortDirection);
        log.info("Completed request for getProductLive the product details");

        return new ResponseEntity<PageableResponse>(allLive, HttpStatus.FOUND);
    }

    @GetMapping("/{productId}")
    ResponseEntity<ProductDto> getProductById(@PathVariable String productId) {

        log.info("Initiated request for getProductById the product details with productId : {}", productId);
        ProductDto product = productService.getProductById(productId);
        log.info("Completed request for getProductById the product details with productId : {}", productId);

        return new ResponseEntity<ProductDto>(product, HttpStatus.FOUND);
    }

    @GetMapping("/{title}")
    ResponseEntity<PageableResponse<ProductDto>> searchProduct(
            @PathVariable String title,
            @RequestParam(value = "pageNumber", defaultValue = "1", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "2", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection
    ) {

        log.info("Initiated request for searchTitle the product details with title : {}", title);
        PageableResponse<ProductDto> productDtos = productService.searchProduct(title, pageNumber, pageSize, sortBy, sortDirection);
        log.info("Completed request for searchTitle the product details with title : {}", title);

        return new ResponseEntity<PageableResponse<ProductDto>>(productDtos, HttpStatus.FOUND);
    }

    @PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponse> uploadProductImage(
            @RequestParam("productImage") MultipartFile image,
            @PathVariable String productId

    ) throws IOException {

        log.info("Initiated request pass service for upload Product image details with userId : {}", productId);

        String imageName = fileService.uploadFile(image, imagePath);

        ProductDto product = productService.getProductById(productId);

        product.setImageProduct(imageName);

        ProductDto productDto = productService.updateProduct(product, productId);

        ImageResponse imageResponse = ImageResponse
                .builder()
                .imageName(imageName)
                .success(HttpStatus.CREATED)
                .status(true)
                .build();

        log.info("Completed request for upload Product image details with userId : {}", productId);

        return new ResponseEntity<ImageResponse>(imageResponse, HttpStatus.CREATED);
    }


    @GetMapping("/image/{productId}")
    public void serveUserImage(
            @PathVariable String productId,
            HttpServletResponse response

    ) throws IOException {

        log.info("Initiated request pass service for serve product image details with productId : {}", productId);

        ProductDto product = productService.getProductById(productId);

        InputStream resource = fileService.getResource(imagePath, product.getImageProduct());

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        StreamUtils.copy(resource, response.getOutputStream());

        log.info("Completed request for serve product image details with productId : {}", productId);
    }
}
