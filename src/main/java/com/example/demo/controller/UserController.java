package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


// Ce contrôleur fonctionne sur le modèle d'une API REST
@RestController
// Ce contrôleur répond à toutes les requêtes sur les endpoints /api/users
@RequestMapping("/api/users")
// Ce contrôleur accepte les requêtes venant d'un serveur différent
@CrossOrigin
public class UserController {

    // Injection de dépendance
    // Une instance de UserRepository est automatiquement créée
    // et rangée dans cette propriété à la construction du contrôleur
    @Autowired
    private UserRepository userRepository;
    
    // Renvoie tous les utilisateurs de la base de données
    @GetMapping("")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Crée un nouveau utilisateur
    @PostMapping("")
    // En cas de succès, renvoie un code HTTP 201 au lieu du code 200 par défaut
    @ResponseStatus(value = HttpStatus.CREATED)
    public User createUser(@Valid @RequestBody User user) {
        // Sauvegarde le utilisateur en BDD et renvoie une copie 
        return userRepository.save(user);
    }

    // Met à jour les propriétés d'un utilisateur déjà existant
    @PutMapping("/{id}")
    public User updateUser(@PathVariable(value = "id") Long userId, @Valid @RequestBody User newUser) {
        // Récupère le utilisateur tel qu'il existe actuellement dans la BDD
        User user = this.fetchUser(userId);
        // Remplace toutes ses propriétés par celles de l'objet entrant
        user.setEmail(newUser.getEmail());
        user.setUsername(newUser.getUsername());
        user.setPassword(newUser.getPassword());
        // Sauvegarde le utilisateur en BDD et renvoie une copie
        return userRepository.save(user);
    }

    // Renvoie un utilisateur particulier de la base de données (en fonction de son id)
    @GetMapping("/{id}")
    public User getUserById(@PathVariable(value = "id") Long userId) {
        return this.fetchUser(userId);
    }

    // Supprimer un utilisateur existant
    @DeleteMapping("/{id}")
    // En cas de succès, renvoie un code HTTP 204 au lieu du code 200 par défaut
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable(value = "id") Long userId) {
        User user = this.fetchUser(userId);
        userRepository.delete(user);
    }

    // Méthode réutilisable permettant d'aller chercher un utilisateur dans la BDD en fonction de son id
    // Renvoie automatiquement une erreur 404 si le utilisateur n'existe pas
    public User fetchUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        );
        return user;
    }
}
