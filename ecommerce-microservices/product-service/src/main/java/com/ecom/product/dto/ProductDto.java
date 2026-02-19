package com.ecom.product.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

    private Long id;
    @NotBlank(message = "Product name is required")
    private String name;
    @NotBlank(message = "Product description is  required")
    private String description;
    @NotBlank(message = "Product price is  required")
    @Positive(message = "Price must be positive")
    private Double price;
    @NotBlank(message = "Product discount price is required")
    private Double discountPrice;
    @NotBlank(message = "Product quantity is  required")
    @Min(value = 0 , message = "Quantity must be 0 or more ")
    private Integer quantity;
    @NotBlank(message = "Product brand name is required")
    private String brand;
    @NotBlank(message = "Product quantity required")
    private String imageUrl;
    @NotNull(message = "Category id is required")
    private Long categoryId;

}
