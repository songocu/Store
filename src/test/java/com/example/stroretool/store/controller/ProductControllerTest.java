package com.example.stroretool.store.controller;

import com.example.stroretool.store.model.Product;
import com.example.stroretool.store.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void home_ReturnsWelcomeMessage() {
        String response = productController.home();
        assertNotNull(response);
        assertTrue(response.contains("Welcome!"));
    }

    @Test
    void addProduct_Success() {
        Product product = new Product();
        product.setName("Test Product");

        when(productService.addProduct(any(Product.class))).thenReturn(product);

        ResponseEntity<Product> response = productController.addProduct(product);

        assertNotNull(response.getBody());
        assertEquals("Test Product", response.getBody().getName());
        verify(productService, times(1)).addProduct(product);
    }

    @Test
    void findProduct_ProductExists() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");

        when(productService.findProduct(1L)).thenReturn(product);

        ResponseEntity<Product> response = productController.findProduct(1L);

        assertNotNull(response.getBody());
        assertEquals("Test Product", response.getBody().getName());
    }

    @Test
    void findProduct_ProductNotFound() {
        when(productService.findProduct(1L)).thenReturn(null);

        ResponseEntity<Product> response = productController.findProduct(1L);

        assertNull(response.getBody());
        assertEquals(404, response.getStatusCodeValue());
    }


    @Test
    void changePrice_ProductNotFound() {
        when(productService.changePrice(1L, BigDecimal.valueOf(150))).thenReturn(null);

        ResponseEntity<Product> response = productController.changePrice(1L, BigDecimal.valueOf(150));

        assertNull(response.getBody());
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void numberOfProductInCategory() {
        when(productService.numberOfProductInCategory("Electronics")).thenReturn(2L);

        ResponseEntity<Long> response = productController.numberOfProductInCategory("Electronics");

        assertNotNull(response.getBody());
        assertEquals(2L, response.getBody());
    }

    @Test
    void howMuchWeSellThisMonth() {
        when(productService.howMuchWeSellThisMonth()).thenReturn(5L);

        ResponseEntity<Long> response = productController.howMuchWeSellThisMonth();

        assertNotNull(response.getBody());
        assertEquals(5L, response.getBody());
    }
}