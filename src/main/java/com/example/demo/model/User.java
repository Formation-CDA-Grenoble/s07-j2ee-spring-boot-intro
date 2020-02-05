package com.example.demo.model;

import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;


// Cette classe est une entité de la BDD
@Entity
// Elle correspond à la table `user`
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
public class User {

    // Cette propriété est la clé primaire de l'entité
    @Id
    // Cette propriété s'auto-incrémente
    @GeneratedValue(strategy = GenerationType.AUTO)
    // Cette propriété correspond à la colonne `id` en BDD
    // Elle est unique et ne peut pas être nulle
    @Column(name = "id", unique = true, nullable = false)
    private long id;
    
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties("user")
    private Set<Order> orders;


    public Set<Order> getOrders() {
    	return this.orders;
    }
    public void setOrders(Set<Order> orders) {
    	this.orders = orders;
    }

    public String getPassword() {
    	return this.password;
    }
    public void setPassword(String password) {
    	this.password = password;
    }

    public String getUsername() {
    	return this.username;
    }
    public void setUsername(String username) {
    	this.username = username;
    }

    public String getEmail() {
    	return this.email;
    }
    public void setEmail(String email) {
    	this.email = email;
    }
    
    public long getId() {
    	return this.id;
    }
    public void setId(long id) {
    	this.id = id;
    }

}
