package com.ecom.product.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {

    private Long id;
    @NotBlank(message = "Category name should not empty")
    private String categoryName;
    private String categoryDescription;
    private Long parentId;

}