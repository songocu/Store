package com.example.stroretool.store.service;


import com.example.stroretool.store.model.Product;
import com.example.stroretool.store.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product addProduct(Product product) {
        product.setAddDate(LocalDateTime.now());
        product.setModifiedDate(LocalDateTime.now());
        return productRepository.save(product);
    }

    public Product findProduct(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product changePrice(Long id, BigDecimal newPrice) {
        Product product = findProduct(id);
        if (product != null) {
            product.setPrice(newPrice);
            product.setModifiedDate(LocalDateTime.now());
            return productRepository.save(product);
        }
        return null;
    }

    public long numberOfProductInCategory(String category) {
        return productRepository.findByCategory(category).size();
    }

    public long howMuchWeSellThisMonth() {
        LocalDateTime startOfMonth = LocalDateTime.now().withDayOfMonth(1).toLocalDate().atStartOfDay();
        return productRepository.findAll().stream()
                .filter(product -> product.getAddDate().isAfter(startOfMonth))
                .count();
    }
}