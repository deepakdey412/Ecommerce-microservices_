package com.ecom.product.services.impl;

import com.ecom.product.dto.ProductDto;
import com.ecom.product.entity.Category;
import com.ecom.product.entity.Product;
import com.ecom.product.mapper.ProductMapper;
import com.ecom.product.repository.ProductRepository;
import com.ecom.product.services.IProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductDto createProduct(ProductDto dto) {
        Category category = null;
        if(dto.getCategoryId() != null){
            category = new Category();
            category.setId(dto.getCategoryId());
        }
        Product product = productMapper.toEntity(dto , category);
        Product savedProduct = productRepository.save(product);

        return productMapper.toDto(savedProduct);
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto dto) {
        Product foundProduct = productRepository.findById(id).orElseThrow(()-> new RuntimeException("Product not found with this id : "+ id));
        foundProduct.setProductName(dto.getName());
        foundProduct.setProductDescription(dto.getDescription());
        foundProduct.setProductPrice(dto.getPrice());
        foundProduct.setProductDiscountPrice(dto.getDiscountPrice());
        if (dto.getCategoryId() != null){
            Category category = new Category();
            category.setId(dto.getCategoryId());
            foundProduct.setProductCategory(category);
        }

        return productMapper.toDto(productRepository.save(foundProduct));
    }

    @Override
    public void deleteProduct(Long id) {
        Product foundProduct = productRepository.findById(id).orElseThrow(()-> new RuntimeException("Product not found with this id : "+ id));
        productRepository.delete(foundProduct);

    }

    @Override
    public ProductDto getProductById(Long id) {
        Product foundProduct = productRepository.findById(id).orElseThrow(()-> new RuntimeException("Product not found with this id : "+ id));
        return productMapper.toDto(foundProduct);
    }

    @Override
    public ProductDto uploadImage(Long id, MultipartFile file) {

        // ✅ Check product exists
        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found with id: " + id));

        // ✅ Check file empty
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("Image file is empty");
        }

        // ✅ File size check (2MB)
        long maxSize = 2 * 1024 * 1024;
        if (file.getSize() > maxSize) {
            throw new RuntimeException("Image size must be less than 2MB");
        }

        try {

            String originalName = file.getOriginalFilename();

            if (originalName == null || !originalName.contains(".")) {
                throw new RuntimeException("Invalid file name");
            }

            // ✅ Extract extension
            String ext = originalName
                    .substring(originalName.lastIndexOf(".") + 1)
                    .toLowerCase();

            // ✅ Validate extension
            List<String> allowedExt = List.of("jpg", "jpeg", "png");

            if (!allowedExt.contains(ext)) {
                throw new RuntimeException("Invalid image extension");
            }

            // ✅ Create folder if not exists
            String uploadDir = "uploads/products/";
            File folder = new File(uploadDir);

            if (!folder.exists()) {
                folder.mkdirs();
            }

            // ✅ Generate unique filename
            String fileName = UUID.randomUUID().toString() + "." + ext;

            Path filePath = Paths.get(uploadDir + fileName);

            // ✅ Save file
            Files.write(filePath, file.getBytes());

            // ✅ Set image URL in product
            String imageUrl = "/products/images/" + fileName;

            product.setProductImageURL(imageUrl);

            Product savedProduct = productRepository.save(product);

            return productMapper.toDto(savedProduct);

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image", e);
        }
    }

    @Override
    public Page<ProductDto> getAllProducts(int page, int size, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> productPage = productRepository.findAll(pageable);

        return productPage.map(productMapper::toDto);
    }

    @Override
    public Page<ProductDto> searchProduct(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.searchProducts(keyword, pageable);
        return productPage.map(productMapper::toDto);
    }

    @Override
    public Page<ProductDto> filterProduct(Long categoryId, Double minPrice, Double maxPrice, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.advanceFilter(null, categoryId, minPrice, maxPrice, pageable);
        return productPage.map(productMapper::toDto);
    }

    @Override
    public Page<ProductDto> advanceFilter(String keyword,
                                          Long categoryId,
                                          Double minPrice,
                                          Double maxPrice,
                                          int page,
                                          int size,
                                          String sortBy,
                                          String sortDirection) {

        // ✅ Check sort direction
        Sort sort = sortDirection != null && sortDirection.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // ✅ Create pageable object
        Pageable pageable = PageRequest.of(page, size, sort);

        // ✅ Handle null price values (avoid null issues in query)
        if (minPrice == null) {
            minPrice = 0.0;
        }

        if (maxPrice == null) {
            maxPrice = Double.MAX_VALUE;
        }

        // ✅ Call repository method with all filters
        Page<Product> productPage = productRepository.advanceFilter(
                keyword,
                categoryId,
                minPrice,
                maxPrice,
                pageable
        );

        // ✅ Convert Entity → DTO using mapper
        return productPage.map(productMapper::toDto);
    }
}
