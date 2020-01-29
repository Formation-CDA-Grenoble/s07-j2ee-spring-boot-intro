package com.example.demo.model;

import javax.persistence.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "product")
@EntityListeners(AuditingEntityListener.class)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "serial_number", nullable = false)
    private String serialNumber;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private float price;

    @Column(name = "stock", nullable = false)
    private int stock;

    @Column(name = "weight", nullable = false)
    private int weight;

    @Column(name = "picture", nullable = false)
    private String picture;
    
    @Column(name = "brand_id", nullable = false)
    private int brandId;
}
