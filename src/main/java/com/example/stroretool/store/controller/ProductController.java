package com.example.stroretool.store.controller;


import com.example.stroretool.store.model.Product;
import com.example.stroretool.store.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private static final Logger logger = LogManager.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @GetMapping("/home")
    public String home() {
        return """
           <h1>Welcome to the Store Tool!</h1>
           <p>This is the home page.</p>
           """;
    }

    @PostMapping("/modify")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        logger.debug("Received request to add product: {}", product);
        return ResponseEntity.ok(productService.addProduct(product));
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<Product> findProduct(@PathVariable Long id) {
        logger.debug("Received request to find product with ID: {}", id);
        Product product = productService.findProduct(id);
        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    @PutMapping("/modify/{id}/price")
    public ResponseEntity<Product> changePrice(@PathVariable Long id, @RequestParam BigDecimal newPrice) {
        logger.debug("Received request to change price for product with ID: {} to {}", id, newPrice);
        Product product = productService.changePrice(id, newPrice);
        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    @GetMapping("/info/count/{category}")
    public ResponseEntity<Long> numberOfProductInCategory(@PathVariable String category) {
        logger.debug("Received request to count products in category: {}", category);
        return ResponseEntity.ok(productService.numberOfProductInCategory(category));
    }

    @GetMapping("/info/sales/this-month")
    public ResponseEntity<Long> howMuchWeSellThisMonth() {
        logger.debug("Received request to count products sold this month");
        return ResponseEntity.ok(productService.howMuchWeSellThisMonth());
    }
}