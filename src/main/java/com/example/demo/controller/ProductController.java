package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    // Injection de dépendance
    @Autowired
    private ProductRepository productRepository;
    
    @GetMapping("")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @PostMapping("")
    public Product createProduct(@Valid @RequestBody Product product) {
        return productRepository.save(product);
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable(value = "id") Long productId) {
        return this.fetchProduct(productId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable(value = "id") Long productId) {
        Product product = this.fetchProduct(productId);
        productRepository.delete(product);
    }

    public Product fetchProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found")
        );
        return product;
    }
}
