package com.ecom.product.services;

import com.ecom.product.dto.ProductDto;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface IProductService {
    ProductDto createProduct(ProductDto dto);
    ProductDto updateProduct(Long id , ProductDto dto);
    void deleteProduct(Long id);
    ProductDto getProductById(Long id);
    ProductDto uploadImage(Long productId , MultipartFile file);
    Page<ProductDto> getAllProducts(int page , int size , String sortBy , String sortDirection);
    Page<ProductDto> searchProduct(String keyword, int page, int size);
    Page<ProductDto> filterProduct(Long categoryId , Double minPrice, Double maxPrice , int page , int size);
    Page<ProductDto> advanceFilter(String keyword, Long CategoryId , Double minPrice , Double maxPrice ,  int page, int size , String sortBy , String sortDirection);

}
