package com.example.demo.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "_order")
@EntityListeners(AuditingEntityListener.class)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @Column(name = "reference")
    private String reference;

    @Column(name = "date")
    private Date date;
    
    @Column(name = "status")
    private int status;

    @ManyToMany(fetch = FetchType.LAZY,
    cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE
    })
    @JoinTable(name = "orders_in_products",
        joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"))    
    @JsonIgnoreProperties("orders")
    private Set<Product> products;


    public Set<Product> getProducts() {
    	return this.products;
    }
    public void setProducts(Set<Product> products) {
    	this.products = products;
    }

    public int getStatus() {
    	return this.status;
    }
    public void setStatus(int status) {
    	this.status = status;
    }

    public Date getDate() {
    	return this.date;
    }
    public void setDate(Date date) {
    	this.date = date;
    }

    public String getReference() {
    	return this.reference;
    }
    public void setReference(String reference) {
    	this.reference = reference;
    }

    public long getId() {
    	return this.id;
    }
    public void setId(long id) {
    	this.id = id;
    }


}
