package com.ecom.product.mapper;

import com.ecom.product.dto.ProductDto;
import com.ecom.product.entity.Product;
import com.ecom.product.entity.Category;

public class ProductMapper {

    public static ProductDto toDto(Product product) {
        if (product == null) return null;

        return ProductDto.builder()
                .id(product.getId())
                .name(product.getProductName())
                .description(product.getProductDescription())
                .price(product.getProductPrice())
                .discountPrice(product.getProductDiscountPrice())
                .quantity(product.getProdutQuantity())
                .brand(product.getProductBrand())
                .imageUrl(product.getProductImageURL())
                .categoryId((long) product.getProductCategory().getId())
                .build();
    }

    public static Product toEntity(ProductDto dto, Category category) {
        if (dto == null) return null;

        return Product.builder()
                .id(dto.getId())
                .productName(dto.getName())
                .productDescription(dto.getDescription())
                .productPrice(dto.getPrice())
                .productDiscountPrice(dto.getDiscountPrice())
                .produtQuantity(dto.getQuantity())
                .productBrand(dto.getBrand())
                .productImageURL(dto.getImageUrl())
                .productCategory(category)
                .build();
    }
}
