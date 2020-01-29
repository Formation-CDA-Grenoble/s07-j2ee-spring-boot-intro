package com.example.demo.controller;

import java.util.List;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    // Injection de dépendance
    @Autowired
    private ProductRepository productRepository;
    
    @GetMapping("/")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
