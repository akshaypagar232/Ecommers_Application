package com.bikked.service.impl;

import com.bikked.dto.CategoryDto;
import com.bikked.dto.PageableResponse;
import com.bikked.dto.ProductDto;
import com.bikked.entity.Category;
import com.bikked.entity.Product;
import com.bikked.repository.CategoryRepository;
import com.bikked.repository.ProductRepository;
import com.bikked.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class ProductServiceImplTest {

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper mapper;

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductService productService;

    Product product;

    Category category;

    @BeforeEach
    void init() {

        category = Category.builder()
                .categoryId("abb")
                .title("smart phone")
                .description("oppo vivo")
                .coverImage("well.jpg")
                .build();

        product = Product.builder()
                .title("phone")
                .live(true)
                .price(5000)
                .stock(true)
                .description("new oppo")
                .quantity(25)
                .discountedPrice(500)
                .imageProduct("abc.jpg")
                .build();
    }

    @Test
    void createProductTest() {

        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);
        ProductDto dto = mapper.map(product, ProductDto.class);
        ProductDto product1 = productService.createProduct(dto);
        Assertions.assertNotNull(product1);
    }

    @Test
    void updateProductTest() {

        String productId = "pro";
        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);
        ProductDto dto = mapper.map(product, ProductDto.class);
        ProductDto productDto = productService.updateProduct(dto, productId);
        Assertions.assertNotNull(productDto);
    }

    @Test
    void deleteProductTest() {

        String productId = "pro";
        Mockito.when(productRepository.findById(Mockito.anyString())).thenReturn(Optional.of(product));
        productService.deleteProduct(productId);
        Mockito.verify(productRepository, Mockito.times(1)).delete(product);
    }

    @Test
    void getAllProductTest() {

        Product product1 = Product.builder()
                .title("Vivo")
                .live(true)
                .price(5000)
                .stock(true)
                .description("new vivo27")
                .quantity(25)
                .discountedPrice(500)
                .imageProduct("abc.jpg")
                .build();
        Product product2 = Product.builder()
                .title("oppo")
                .live(true)
                .price(5000)
                .stock(true)
                .description("new oppo73")
                .quantity(25)
                .discountedPrice(500)
                .imageProduct("abc.jpg")
                .build();

        List<Product> productList = Arrays.asList(product1, product2);
        Page<Product> pro = new PageImpl<>(productList);
        Mockito.when(productRepository.findAll((Pageable) Mockito.any())).thenReturn(pro);
        PageableResponse<ProductDto> allProduct = productService.getAllProduct(0, 2, "title", "asc");
        Assertions.assertEquals(2, allProduct.getContent().size());
    }

    @Test
    void getProductByIdTest() {

        String productId = "prod";
        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        ProductDto product1 = productService.getProductById(productId);
        Assertions.assertEquals(product1.getTitle(), "phone");
    }

    @Test
    void getAllLiveTest() {

        Product product1 = Product.builder()
                .title("Vivo")
                .live(true)
                .price(5000)
                .stock(true)
                .description("new vivo27")
                .quantity(25)
                .discountedPrice(500)
                .imageProduct("abc.jpg")
                .build();
        Product product2 = Product.builder()
                .title("oppo")
                .live(true)
                .price(5000)
                .stock(true)
                .description("new oppo73")
                .quantity(25)
                .discountedPrice(500)
                .imageProduct("abc.jpg")
                .build();

        List<Product> productList = Arrays.asList(product1, product2);
        Page<Product> pro = new PageImpl<>(productList);
        Mockito.when(productRepository.findByLiveTrue((Pageable) Mockito.any())).thenReturn(pro);
        PageableResponse<ProductDto> allLive = productService.getAllLive(0, 2, "title", "asc");
        Assertions.assertEquals(2, allLive.getContent().size());
    }

    @Test
    void searchProductTest() {

        String keyword = "vivo";
        Product product1 = Product.builder()
                .title("vivo")
                .live(true)
                .price(5000)
                .stock(true)
                .description("new vivo 56")
                .quantity(25)
                .discountedPrice(500)
                .imageProduct("abc.jpg")
                .build();
        Product product2 = Product.builder()
                .title("oppo")
                .live(true)
                .price(5000)
                .stock(true)
                .description("new oppo 23")
                .quantity(25)
                .discountedPrice(500)
                .imageProduct("abc.jpg")
                .build();

        List<Product> productList = Arrays.asList(product1, product2);
        Page<Product> pro = new PageImpl<>(productList);
        Mockito.when(productRepository.findByTitleContaining(Mockito.anyString(), Mockito.any())).thenReturn(pro);
        PageableResponse<ProductDto> searchedProduct = productService.searchProduct(keyword, 0, 4, "title", "asc");
        Assertions.assertNotNull(searchedProduct);
        Assertions.assertEquals(2, searchedProduct.getContent().size(), "Test case failed");
    }

    @Test
    void createWithCategoryTest() {

        Product product1 = Product.builder()
                .title("phone")
                .live(true)
                .price(5000)
                .stock(true)
                .description("new oppo")
                .quantity(25)
                .discountedPrice(500)
                .imageProduct("abc.jpg")
                .category(category)
                .build();
        Mockito.when(categoryRepository.findById(Mockito.anyString())).thenReturn(Optional.of(category));
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product1);
        ProductDto productDto = mapper.map(product1, ProductDto.class);
        ProductDto withCategory = productService.createWithCategory(productDto, category.getCategoryId());
        Assertions.assertEquals(withCategory.getPrice(),productDto.getPrice());
    }
}