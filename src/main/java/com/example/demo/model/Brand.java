package com.example.demo.model;

import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "brand")
@EntityListeners(AuditingEntityListener.class)
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "country", nullable = false)
    private String country;

    @OneToMany(mappedBy = "brand")
    @JsonIgnoreProperties("brand")
    private Set<Product> products;


    public Set<Product> getProducts() {
    	return this.products;
    }
    public void setProducts(Set<Product> products) {
    	this.products = products;
    }


    public String getCountry() {
    	return this.country;
    }
    public void setCountry(String country) {
    	this.country = country;
    }


    public String getName() {
    	return this.name;
    }
    public void setName(String name) {
    	this.name = name;
    }


    public long getId() {
    	return this.id;
    }
    public void setId(long id) {
    	this.id = id;
    }

}
