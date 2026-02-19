package com.ecom.product.mapper;

import com.ecom.product.dto.CategoryDto;
import com.ecom.product.entity.Category;

public class CategoryMapper {

    public static CategoryDto toDto(Category category) {
        if (category == null) return null;

        return CategoryDto.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .categoryDescription(category.getCategoryDescription())
                .parentId(category.getParentId())
                .build();
    }

    public static Category toEntity(CategoryDto dto) {
        if (dto == null) return null;

        return Category.builder()
                .id(dto.getId())
                .categoryName(dto.getCategoryName())
                .categoryDescription(dto.getCategoryDescription())
                .parentId(dto.getParentId())
                .build();
    }
}
