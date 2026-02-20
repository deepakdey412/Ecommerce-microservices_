package com.ecom.product.services;

import com.ecom.product.dto.CategoryDto;

import java.util.List;

public interface ICategoryService {
    CategoryDto create(CategoryDto categoryDto);
    CategoryDto update(Long id , CategoryDto categoryDto);
    void delete(Long id);
    CategoryDto getCategoryById(Long id);
    List<CategoryDto> getAllCategory();
}
