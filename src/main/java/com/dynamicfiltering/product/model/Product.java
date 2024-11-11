package com.dynamicfiltering.product.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    private String description;

    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    private Integer stockQuantity;

    private Double rating;

    private Integer reviewCount;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Image> images;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Tag> tags;

    @ElementCollection
    @CollectionTable(name = "product_specification", joinColumns = @JoinColumn(name = "product_id"))
    @MapKeyColumn(name = "spec_name")
    @Column(name = "spec_value")
    private Map<String, String> specifications;

    @ElementCollection
    @CollectionTable(name = "product_color", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "color")
    private List<String> colors;

    private Double weight;

    private Double dimensionsLength;

    private Double dimensionsWidth;

    private Double dimensionsHeight;

    @ManyToMany
    @JoinTable(
            name = "related_products",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "related_product_id")
    )
    private List<Product> similarProducts;
}
