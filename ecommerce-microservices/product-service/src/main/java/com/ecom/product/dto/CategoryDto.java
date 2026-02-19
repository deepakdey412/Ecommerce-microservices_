package com.ecom.product.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {

    private Long id;
    private String categoryName;
    private String categoryDescription;
    private Long parentId;

}