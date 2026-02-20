package com.ecom.product.controller;

import com.ecom.product.dto.ProductDto;
import com.ecom.product.entity.Product;
import com.ecom.product.services.IProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto dto) {
        return ResponseEntity.ok(productService.createProduct(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id , @RequestBody ProductDto dto) {
        return ResponseEntity.ok(productService.updateProduct(id,dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) {
        return  ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<Page<ProductDto>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {

        Page<ProductDto> products = productService.getAllProducts(page, size, sortBy, sortDirection);

        return ResponseEntity.ok(products);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductDto>> searchProducts(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Page<ProductDto> products = productService.searchProduct(keyword, page, size);

        return ResponseEntity.ok(products);
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<ProductDto>> filterProducts(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Page<ProductDto> products =
                productService.filterProduct(categoryId, minPrice, maxPrice, page, size);

        return ResponseEntity.ok(products);
    }

    @GetMapping("/advance-filter")
    public ResponseEntity<Page<ProductDto>> advanceFilter(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {

        Page<ProductDto> products =
                productService.advanceFilter(
                        keyword, categoryId, minPrice, maxPrice,
                        page, size, sortBy, sortDirection
                );

        return ResponseEntity.ok(products);
    }

    @PostMapping("/{id}/upload-image")
    public ResponseEntity<ProductDto> uploadImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file
    ) {

        ProductDto updatedProduct = productService.uploadImage(id, file);

        return ResponseEntity.ok(updatedProduct);
    }

}
