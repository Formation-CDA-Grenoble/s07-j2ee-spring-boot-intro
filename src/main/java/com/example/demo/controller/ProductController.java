package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductController {

    // Injection de dépendance
    @Autowired
    private ProductRepository productRepository;
    
    // Renvoie tous les produits de la base de données
    @GetMapping("")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Crée un nouveau produit
    @PostMapping("")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Product createProduct(@Valid @RequestBody Product product) {
        return productRepository.save(product);
    }

    // Met à jour les propriétés d'un produit déjà existant
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable(value = "id") Long productId, @Valid @RequestBody Product newProduct) {
        Product product = this.fetchProduct(productId);
        product.setName(newProduct.getName());
        product.setSerialNumber(newProduct.getSerialNumber());
        product.setDescription(newProduct.getDescription());
        product.setPrice(newProduct.getPrice());
        product.setStock(newProduct.getStock());
        product.setWeight(newProduct.getWeight());
        product.setPicture(newProduct.getPicture());
        product.setBrand(newProduct.getBrand());
        return productRepository.save(product);
    }

    // Renvoie un produit particulier de la base de données (en fonction de son id)
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable(value = "id") Long productId) {
        return this.fetchProduct(productId);
    }

    // Supprimer un produit existant
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable(value = "id") Long productId) {
        Product product = this.fetchProduct(productId);
        productRepository.delete(product);
    }

    // Méthode réutilisable permettant d'aller chercher un produit dans la BDD en fonction de son id
    // Renvoie automatiquement une erreur 404 si le produit n'existe pas
    public Product fetchProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found")
        );
        return product;
    }
}
