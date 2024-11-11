package com.dynamicfiltering.product.controller;

import com.dynamicfiltering.product.service.ProductService;
import com.dynamicfiltering.product.utility.ApiResponse;
import com.dynamicfiltering.product.DAO.ProductDTO;
import com.dynamicfiltering.product.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // CREATE a new product
    @PostMapping
    public ResponseEntity<ApiResponse<ProductDTO>> createProduct(@RequestBody ProductDTO productDTO) {
        Product createdProduct = productService.createProduct(productDTO);
        ProductDTO responseDTO = productService.mapToDTO(createdProduct);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("/api/v1/products", responseDTO));
    }

    // READ a product by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDTO>> getProductById(@PathVariable String id) {
        Optional<ProductDTO> product = productService.getProductById(id);
        if (product.isPresent()) {
            return ResponseEntity.ok(ApiResponse.success("/api/v1/products/" + id, product.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("/api/v1/products/" + id, "Product not found", 404));
        }
    }

    // READ all products
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(ApiResponse.success("/api/v1/products", products));
    }

    // UPDATE a product by ID
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDTO>> updateProduct(@PathVariable String id, @RequestBody ProductDTO productDTO) {
        Optional<Product> updatedProduct = productService.updateProduct(id, productDTO);
        if (updatedProduct.isPresent()) {
            ProductDTO responseDTO = productService.mapToDTO(updatedProduct.get());
            return ResponseEntity.ok(ApiResponse.success("/api/v1/products/" + id, responseDTO));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("/api/v1/products/" + id, "Product not found", 404));
        }
    }

    // DELETE a product by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ApiResponse.success("/api/v1/products/" + id, null));
    }
}
