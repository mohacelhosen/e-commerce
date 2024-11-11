package com.dynamicfiltering.product.service;

import com.dynamicfiltering.product.DAO.ProductDTO;
import com.dynamicfiltering.product.model.Image;
import com.dynamicfiltering.product.model.Product;
import com.dynamicfiltering.product.model.Tag;
import com.dynamicfiltering.product.repository.BrandRepository;
import com.dynamicfiltering.product.repository.CategoryRepository;
import com.dynamicfiltering.product.repository.ImageRepository;
import com.dynamicfiltering.product.repository.ProductRepository;
import com.dynamicfiltering.product.repository.TagRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final TagRepository tagRepository;
    private final BrandRepository brandRepository;
    @Autowired
    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository,
                          ImageRepository imageRepository,
                          TagRepository tagRepository,
                          BrandRepository brandRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.imageRepository = imageRepository;
        this.tagRepository = tagRepository;
        this.brandRepository = brandRepository;
    }

    // CREATE a product
    @Transactional
    public Product createProduct(ProductDTO productDTO) {
        Product product = mapToEntity(productDTO);
        return productRepository.save(product);
    }

    // READ a single product by ID
    public Optional<ProductDTO> getProductById(String id) {
        return productRepository.findById(Long.valueOf(id)).map(this::mapToDTO);
    }

    // READ all products
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // UPDATE a product by ID
    @Transactional
    public Optional<Product> updateProduct(String id, ProductDTO productDTO) {
        return productRepository.findById(Long.valueOf(id)).map(existingProduct -> {
            Product updatedProduct = mapToEntity(productDTO);
            updatedProduct.setId(id);  // Keep the same ID
            return productRepository.save(updatedProduct);
        });
    }

    // DELETE a product by ID
    @Transactional
    public void deleteProduct(String id) {
        productRepository.deleteById(Long.valueOf(id));
    }

    // Helper method to map ProductDTO to Product entity
    private Product mapToEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStockQuantity(productDTO.getStockQuantity());
        product.setRating(productDTO.getRating());
        product.setReviewCount(productDTO.getReviewCount());
        product.setSpecifications(productDTO.getSpecifications());
        product.setColors(productDTO.getColors());
        product.setWeight(productDTO.getWeight());
        product.setDimensionsLength(productDTO.getDimensionsLength());
        product.setDimensionsWidth(productDTO.getDimensionsWidth());
        product.setDimensionsHeight(productDTO.getDimensionsHeight());

        // Set Category relationship
        categoryRepository.findById(productDTO.getCategoryId())
                .ifPresent(product::setCategory);

        // Set Brand relationship
        brandRepository.findById(Math.toIntExact(productDTO.getBrandId()))
                .ifPresent(product::setBrand);

        // Set Images relationship
        List<Image> images = productDTO.getImageUrls().stream()
                .map(url -> new Image(null, url, product))  // Assuming Image constructor takes (id, url, product)
                .collect(Collectors.toList());
        product.setImages(images);

        // Set Tags relationship
        List<Tag> tags = productDTO.getTagNames().stream()
                .map(name -> new Tag(null, name, product))  // Assuming Tag constructor takes (id, name, product)
                .collect(Collectors.toList());
        product.setTags(tags);

        return product;
    }

    // Helper method to map Product entity to ProductDTO
    public ProductDTO mapToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStockQuantity(product.getStockQuantity());
        dto.setRating(product.getRating());
        dto.setReviewCount(product.getReviewCount());
        dto.setSpecifications(product.getSpecifications());
        dto.setColors(product.getColors());
        dto.setWeight(product.getWeight());
        dto.setDimensionsLength(product.getDimensionsLength());
        dto.setDimensionsWidth(product.getDimensionsWidth());
        dto.setDimensionsHeight(product.getDimensionsHeight());

        // Map Category relationship
        dto.setCategoryId(product.getCategory() != null ? product.getCategory().getId() : null);

        // Map Brand relationship
        dto.setBrandId(product.getBrand() != null ? product.getBrand().getId() : null);

        // Map Images relationship
        List<String> imageUrls = product.getImages().stream()
                .map(Image::getUrl)
                .collect(Collectors.toList());
        dto.setImageUrls(imageUrls);

        // Map Tags relationship
        List<String> tagNames = product.getTags().stream()
                .map(Tag::getName)
                .collect(Collectors.toList());
        dto.setTagNames(tagNames);

        return dto;
    }
}
