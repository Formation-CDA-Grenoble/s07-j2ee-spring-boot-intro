package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import com.example.demo.model.Order;
import com.example.demo.model.Product;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


// Ce contrôleur fonctionne sur le modèle d'une API REST
@RestController
// Ce contrôleur répond à toutes les requêtes sur les endpoints /api/orders
@RequestMapping("/api/orders")
// Ce contrôleur accepte les requêtes venant d'un serveur différent
@CrossOrigin
public class OrderController {

    // Injection de dépendance
    // Une instance de OrderRepository est automatiquement créée
    // et rangée dans cette propriété à la construction du contrôleur
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    
    // Renvoie tous les commandes de la base de données
    @GetMapping("")
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Crée un nouveau commande
    @PostMapping("")
    // En cas de succès, renvoie un code HTTP 201 au lieu du code 200 par défaut
    @ResponseStatus(value = HttpStatus.CREATED)
    public Order createOrder(@Valid @RequestBody Order order) {
        // Sauvegarde le commande en BDD et renvoie une copie 
        return orderRepository.save(order);
    }

    // Met à jour les propriétés d'un commande déjà existant
    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable(value = "id") Long orderId, @Valid @RequestBody Order newOrder) {
        // Récupère le commande tel qu'il existe actuellement dans la BDD
        Order order = this.fetchOrder(orderId);
        // Remplace toutes ses propriétés par celles de l'objet entrant
        order.setReference(newOrder.getReference());
        order.setStatus(newOrder.getStatus());
        order.setDate(newOrder.getDate());
        // Sauvegarde le commande en BDD et renvoie une copie
        return orderRepository.save(order);
    }

    // Renvoie un commande particulier de la base de données (en fonction de son id)
    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable(value = "id") Long orderId) {
        return this.fetchOrder(orderId);
    }

    // Supprimer un commande existant
    @DeleteMapping("/{id}")
    // En cas de succès, renvoie un code HTTP 204 au lieu du code 200 par défaut
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable(value = "id") Long orderId) {
        Order order = this.fetchOrder(orderId);
        orderRepository.delete(order);
    }

    @GetMapping("/{orderId}/addProduct/{productId}")
    public Order addProduct(
        @PathVariable(value = "orderId") Long orderId,
        @PathVariable(value = "productId") Long productId
    ) {
        Order order = this.fetchOrder(orderId);
        Product product = productRepository.findById(productId).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found")
        );

        if (order.getProducts().contains(product)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product already present in order");
        }

        order.getProducts().add(product);
        return orderRepository.save(order);
    }

    @GetMapping("/{orderId}/removeProduct/{productId}")
    public Order removeProduct(
        @PathVariable(value = "orderId") Long orderId,
        @PathVariable(value = "productId") Long productId
    ) {
        Order order = this.fetchOrder(orderId);
        Product product = productRepository.findById(productId).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found")
        );

        if (!order.getProducts().contains(product)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product not present in order");
        }

        order.getProducts().remove(product);
        return orderRepository.save(order);
    }

    // Méthode réutilisable permettant d'aller chercher un commande dans la BDD en fonction de son id
    // Renvoie automatiquement une erreur 404 si le commande n'existe pas
    public Order fetchOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found")
        );
        return order;
    }
}
