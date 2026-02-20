package com.ecom.product.services;

import com.ecom.product.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductService {
    ProductDto createProduct(ProductDto dto);
    ProductDto updateProduct(Long id , ProductDto dto);
    void deleteProduct(Long id);
    ProductDto getProductById(Long id);
    Page<ProductDto> getAllProducts(int page , int size , String sortBy , String sortDirection);
    Page<ProductDto> searchProduct(String keyword, int page, int size);
    Page<ProductDto> filterProduct(Long categoryId , Double minPrice, Double maxPrice , int page , int size);
    Page<ProductDto> advanceFilter(String keyword, Long CategoryId , Double minPrice , Double maxPrice ,  int page, int size , String sortBy , String sortDirection);

}
