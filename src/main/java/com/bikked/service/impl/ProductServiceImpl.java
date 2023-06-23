package com.bikked.service.impl;

import com.bikked.constant.AppConstant;
import com.bikked.dto.PageableResponse;
import com.bikked.dto.ProductDto;
import com.bikked.entity.Category;
import com.bikked.entity.Product;
import com.bikked.exceptions.ResourceNotFoundException;
import com.bikked.helper.Helper;
import com.bikked.repository.CategoryRepository;
import com.bikked.repository.ProductRepository;
import com.bikked.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper mapper;

    @Value("${product.image.path}")
    private String imagePath;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ProductDto createProduct(ProductDto productDto) {

        log.info("Initiated request for save product details in database");
        String Id = UUID.randomUUID().toString();
        productDto.setProductId(Id);
        productDto.setAddedDate(new Date());
        Product product = mapper.map(productDto, Product.class);
        Product save = productRepository.save(product);
        ProductDto map = mapper.map(save, ProductDto.class);
        log.info("Competed request for save product details in database");

        return map;
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, String productId) {

        log.info("Initiated request for update product details in database with productId : {}", productId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.PRODUCT_ID));
        Product map = mapper.map(productDto, Product.class);
        product.setAddedDate(map.getAddedDate());
        product.setPrice(map.getPrice());
        product.setDescription(map.getDescription());
        product.setQuantity(map.getQuantity());
        product.setTitle(map.getTitle());
        product.setDiscountedPrice(map.getDiscountedPrice());
        product.setImageProduct(map.getImageProduct());
        Product save = productRepository.save(product);
        ProductDto dto = mapper.map(save, ProductDto.class);
        log.info("Completed request for update product details in database with productId : {}", productId);

        return dto;
    }

    @Override
    public void deleteProduct(String productId) {

        log.info("Initiated request for delete product details in database with productId : {}", productId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.PRODUCT_ID));
        String image = imagePath + product.getImageProduct();
        try {
            Path path = Paths.get(image);
            Files.delete(path);
        } catch (NoSuchFileException ex) {
            log.info("user image not found in folder");
            ex.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        productRepository.delete(product);
        log.info("Completed request for delete product details in database with productId : {}", productId);
    }

    @Override
    public PageableResponse<ProductDto> getAllProduct(int pageNumber, int pageSize, String sortBy, String sortDirection) {

        log.info("Initiated request for getAllProduct product details in database");
        Sort sort = sortDirection.equalsIgnoreCase("desc") ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> all = productRepository.findAll(pageRequest);
        PageableResponse<ProductDto> Response = Helper.getPagableResponse(all, ProductDto.class);
        log.info("Completed request for getAllProduct product details in database");

        return Response;
    }

    @Override
    public ProductDto getProductById(String productId) {

        log.info("Initiated request for getProductById product details in database with productId : {}", productId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.PRODUCT_ID));
        ProductDto dto = mapper.map(product, ProductDto.class);
        log.info("Completed request for getProductById product details in database with productId : {}", productId);

        return dto;
    }

    @Override
    public PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDirection) {

        log.info("Initiated request for getAllLive product details in database");
        Sort sort = sortDirection.equalsIgnoreCase("desc") ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> liveTrue = productRepository.findByLiveTrue(pageRequest);
        PageableResponse<ProductDto> response = Helper.getPagableResponse(liveTrue, ProductDto.class);
        log.info("Completed request for getAllLive product details in database");

        return response;
    }

    @Override
    public PageableResponse<ProductDto> searchProduct(String title, int pageNumber, int pageSize, String sortBy, String sortDirection) {

        log.info("Initiated request for searchTitle product details in database with title : {}", title);
        Sort sort = sortDirection.equalsIgnoreCase("desc") ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> products = productRepository.findByTitleContaining(title, pageRequest);
        PageableResponse<ProductDto> response = Helper.getPagableResponse(products, ProductDto.class);
        log.info("Completed request for searchTitle product details in database with title : {}", title);

        return response;
    }

    @Override
    public ProductDto createWithCategory(ProductDto productDto, String categoryId) {
        log.info("Initiated request for createWithCategory category details in database with categoryId : {}", categoryId);
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.CATEGORY_ID));
        String Id = UUID.randomUUID().toString();
        productDto.setProductId(Id);
        productDto.setAddedDate(new Date());
        Product product = mapper.map(productDto, Product.class);
        product.setCategory(category);
        Product save = productRepository.save(product);
        ProductDto map = mapper.map(save, ProductDto.class);
        log.info("Competed request for save createWithCategory category details in database  with categoryId : {}", categoryId);

        return map;

    }
}