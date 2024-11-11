package com.dynamicfiltering.product.DAO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private String id;
    private String name;
    private String description;
    private Double price;
    private Long categoryId;
    private Long brandId;
    private Integer stockQuantity;
    private Double rating;
    private Integer reviewCount;
    private List<String> imageUrls;
    private List<String> tagNames;
    private Map<String, String> specifications;
    private List<String> colors;
    private Double weight;
    private Double dimensionsLength;
    private Double dimensionsWidth;
    private Double dimensionsHeight;
    private List<String> similarProductIds;
}
