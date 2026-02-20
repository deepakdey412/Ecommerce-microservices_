package com.ecom.product.services.impl;

import com.ecom.product.dto.ProductDto;
import com.ecom.product.entity.Category;
import com.ecom.product.entity.Product;
import com.ecom.product.mapper.ProductMapper;
import com.ecom.product.repository.ProductRepository;
import com.ecom.product.services.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

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
    public ProductDto uploadImage(Long id , MultipartFile file) {
        Product foundProduct = productRepository.findById(id).orElseThrow(()-> new RuntimeException("Product not found with this id : "+ id));

        if (file.isEmpty()){
            throw new RuntimeException("Image file is empty");
        }

        long imageFileSize = 2*1024*1024;

        if (file.getSize()>imageFileSize){
            throw new RuntimeException("Image file is too large , must be 2MB");
        }
        List<String> allowedExtensions = Arrays.asList("image/jpg", "image/png");

        if (!allowedExtensions.contains(file.getContentType())){
            throw new RuntimeException("Image file is not allowed");
        }

        String fileName = file.getOriginalFilename();
        String extension = fileName.substring(fileName.lastIndexOf("."));

        return null;
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
