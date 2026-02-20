package com.ecom.product.services.impl;

import com.ecom.product.dto.CategoryDto;
import com.ecom.product.entity.Category;
import com.ecom.product.mapper.CategoryMapper;
import com.ecom.product.repository.CategoryRepository;
import com.ecom.product.services.ICategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository,
                           CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    // ✅ CREATE
    @Override
    public CategoryDto create(CategoryDto categoryDto) {

        // 🔥 Check duplicate name
        if (categoryRepository.existsByName(categoryDto.getCategoryName())) {
            throw new RuntimeException("Category name already exists");
        }

        Category newCategory = categoryMapper.toEntity(categoryDto);
        Category savedCategory = categoryRepository.save(newCategory);

        return categoryMapper.toDto(savedCategory);
    }

    // ✅ UPDATE
    @Override
    public CategoryDto update(CategoryDto categoryDto) {

        // 🔥 Check category exists
        Category existingCategory = categoryRepository.findById(categoryDto.getId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: "
                        + categoryDto.getId()));

        // 🔥 Check duplicate name (if changed)
        if (!existingCategory.getCategoryName().equals(categoryDto.getCategoryName())
                && categoryRepository.existsByName(categoryDto.getCategoryName())) {

            throw new RuntimeException("Category name already exists");
        }

        existingCategory.setCategoryName(categoryDto.getCategoryName());

        Category updatedCategory = categoryRepository.save(existingCategory);

        return categoryMapper.toDto(updatedCategory);
    }

    // ✅ DELETE
    @Override
    public void delete(Long id) {

        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        categoryRepository.delete(existingCategory);
    }

    // ✅ GET BY ID
    @Override
    public CategoryDto getCategoryById(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        return categoryMapper.toDto(category);
    }

    // ✅ GET ALL
    @Override
    public List<CategoryDto> getAllCategory() {

        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
                .map(categoryMapper::toDto)
                .toList();
    }
}