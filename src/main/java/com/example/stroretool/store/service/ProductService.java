package com.example.stroretool.store.service;


import com.example.stroretool.store.model.Product;
import com.example.stroretool.store.repository.ProductRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class ProductService {

    private static final Logger logger = LogManager.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    public Product addProduct(Product product) {
        logger.info("Adding new product: {}", product.getName());
        product.setAddDate(LocalDateTime.now());
        product.setModifiedDate(LocalDateTime.now());
        Product savedProduct = productRepository.save(product);
        logger.info("Product added with ID: {}", savedProduct.getId());
        return savedProduct;
    }

    public Product findProduct(Long id) {
        logger.info("Finding product with ID: {}", id);
        return productRepository.findById(id).orElse(null);
    }

    public Product changePrice(Long id, BigDecimal newPrice) {
        logger.info("Changing price for product with ID: {} to {}", id, newPrice);
        Product product = findProduct(id);
        if (product != null) {
            product.setPrice(newPrice);
            product.setModifiedDate(LocalDateTime.now());
            Product updatedProduct = productRepository.save(product);
            logger.info("Price updated for product with ID: {}", updatedProduct.getId());
            return updatedProduct;
        }
        logger.warn("Product with ID: {} not found", id);
        return null;
    }

    public long numberOfProductInCategory(String category) {
        logger.info("Counting products in category: {}", category);
        return productRepository.findByCategory(category).size();
    }

    public long howMuchWeSellThisMonth() {
        LocalDateTime startOfMonth = LocalDateTime.now().withDayOfMonth(1).toLocalDate().atStartOfDay();
        logger.info("Counting products sold this month since: {}", startOfMonth);
        return productRepository.findAll().stream()
                .filter(product -> product.getAddDate().isAfter(startOfMonth))
                .count();
    }
}