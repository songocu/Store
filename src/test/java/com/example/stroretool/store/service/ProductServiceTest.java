package com.example.stroretool.store.service;

import com.example.stroretool.store.model.Product;
import com.example.stroretool.store.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addProduct_Success() {
        Product product = new Product();
        product.setName("Test Product");

        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
            Product savedProduct = invocation.getArgument(0);
            savedProduct.setId(1L);
            return savedProduct;
        });

        Product savedProduct = productService.addProduct(product);

        assertNotNull(savedProduct.getId());
        assertEquals("Test Product", savedProduct.getName());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void findProduct_ProductExists() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product foundProduct = productService.findProduct(1L);

        assertNotNull(foundProduct);
        assertEquals("Test Product", foundProduct.getName());
    }

    @Test
    void findProduct_ProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        Product foundProduct = productService.findProduct(1L);

        assertNull(foundProduct);
    }

    @Test
    void changePrice_ProductExists() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setPrice(BigDecimal.valueOf(100));

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product updatedProduct = productService.changePrice(1L, BigDecimal.valueOf(150));

        assertNotNull(updatedProduct);
        assertEquals(BigDecimal.valueOf(150), updatedProduct.getPrice());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void changePrice_ProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        Product updatedProduct = productService.changePrice(1L, BigDecimal.valueOf(150));

        assertNull(updatedProduct);
    }

    @Test
    void numberOfProductInCategory() {
        when(productRepository.findByCategory("Electronics")).thenReturn(Arrays.asList(new Product(), new Product()));

        long count = productService.numberOfProductInCategory("Electronics");

        assertEquals(2, count);
    }

    @Test
    void howMuchWeSellThisMonth() {
        Product product1 = new Product();
        product1.setAddDate(LocalDateTime.now().minusDays(5));
        Product product2 = new Product();
        product2.setAddDate(LocalDateTime.now().minusDays(10));
        Product product3 = new Product();
        product3.setAddDate(LocalDateTime.now().minusMonths(1));

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2, product3));

        long count = productService.howMuchWeSellThisMonth();

        assertEquals(2, count);
    }
}