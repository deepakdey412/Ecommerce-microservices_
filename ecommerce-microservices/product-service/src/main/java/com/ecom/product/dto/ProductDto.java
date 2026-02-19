package com.ecom.product.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private Double discountPrice;
    private Integer quantity;
    private String brand;
    private String imageUrl;
    private Long categoryId;

}
