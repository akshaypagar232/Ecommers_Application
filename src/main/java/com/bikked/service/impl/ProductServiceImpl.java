package com.bikked.service.impl;

import com.bikked.constant.AppConstant;
import com.bikked.dto.PageableResponse;
import com.bikked.dto.ProductDto;
import com.bikked.entity.Product;
import com.bikked.exceptions.ResourceNotFoundException;
import com.bikked.repository.ProductRepository;
import com.bikked.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public ProductDto createProduct(ProductDto productDto) {

        log.info("Initiated request for save product details in database");
        String Id = UUID.randomUUID().toString();
        productDto.setProductId(Id);
        Product product = mapper.map(productDto, Product.class);
        Product save = productRepository.save(product);
        ProductDto map = mapper.map(save, ProductDto.class);
        log.info("Competed request for save product details in database");

        return map;
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, String productId) {

        log.info("Initiated request for update product details in database with productId : {}", productId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.Product));
        Product map = mapper.map(productDto, Product.class);
        product.setAddedDate(map.getAddedDate());
        product.setPrice(map.getPrice());
        product.setDescription(map.getDescription());
        product.setQuantity(map.getQuantity());
        product.setTitle(map.getTitle());
        product.setDiscountedPrice(map.getDiscountedPrice());
        Product save = productRepository.save(product);
        ProductDto dto = mapper.map(save, ProductDto.class);
        log.info("Completed request for update product details in database with productId : {}", productId);

        return dto;
    }

    @Override
    public void deleteProduct(String productId) {
       
        log.info("Initiated request for delete product details in database with productId : {}", productId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.Product));
        productRepository.delete(product);
        log.info("Completed request for delete product details in database with productId : {}", productId);

    }

    @Override
    public PageableResponse<ProductDto> getAllProduct(int pageNumber, int pageSize, String sortBy, String sortDirection) {

        log.info("Initiated request for getAllProduct product details in database");
        Sort orders = sortDirection.equalsIgnoreCase("desc") ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        List<Product> all = productRepository.findAll(orders);

        return null;
    }

    @Override
    public ProductDto getProductById(ProductDto productDto) {
        return null;
    }
}
