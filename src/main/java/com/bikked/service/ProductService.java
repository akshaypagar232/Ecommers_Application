package com.bikked.service;

import com.bikked.dto.PageableResponse;
import com.bikked.dto.ProductDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    ProductDto createProduct(ProductDto productDto);

    ProductDto updateProduct(ProductDto productDto, String productId);

    void deleteProduct(String productId);

    PageableResponse<ProductDto> getAllProduct(int pageNumber, int pageSize, String sortBy, String sortDirection);

    ProductDto getProductById(String productId);

    PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDirection);

    PageableResponse<ProductDto> searchProduct(String title,int pageNumber, int pageSize, String sortBy, String sortDirection );

    ProductDto createWithCategory(ProductDto productDto,String categoryId);
}
