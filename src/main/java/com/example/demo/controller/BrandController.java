package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import com.example.demo.model.Brand;
import com.example.demo.repository.BrandRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/brands")
public class BrandController {

    // Injection de dépendance
    @Autowired
    private BrandRepository brandRepository;
    
    // Renvoie tous les produits de la base de données
    @GetMapping("")
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    // Crée un nouveau produit
    @PostMapping("")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Brand createBrand(@Valid @RequestBody Brand brand) {
        return brandRepository.save(brand);
    }

    // Met à jour les propriétés d'un produit déjà existant
    @PutMapping("/{id}")
    public Brand updateBrand(@PathVariable(value = "id") Long brandId, @Valid @RequestBody Brand newBrand) {
        Brand brand = this.fetchBrand(brandId);
        brand.setName(newBrand.getName());
        brand.setCountry(newBrand.getCountry());
        return brandRepository.save(brand);
    }

    // Renvoie un produit particulier de la base de données (en fonction de son id)
    @GetMapping("/{id}")
    public Brand getBrandById(@PathVariable(value = "id") Long brandId) {
        return this.fetchBrand(brandId);
    }

    // Supprimer un produit existant
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteBrand(@PathVariable(value = "id") Long brandId) {
        Brand brand = this.fetchBrand(brandId);
        brandRepository.delete(brand);
    }

    // Méthode réutilisable permettant d'aller chercher un produit dans la BDD en fonction de son id
    // Renvoie automatiquement une erreur 404 si le produit n'existe pas
    public Brand fetchBrand(Long brandId) {
        Brand brand = brandRepository.findById(brandId).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Brand not found")
        );
        return brand;
    }
}
