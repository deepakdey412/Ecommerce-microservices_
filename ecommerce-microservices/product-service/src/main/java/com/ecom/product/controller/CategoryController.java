package com.ecom.product.controller;

import com.ecom.product.dto.CategoryDto;
import com.ecom.product.services.ICategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final ICategoryService categoryService;

    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(
            @RequestBody CategoryDto categoryDto) {

        CategoryDto createdCategory = categoryService.create(categoryDto);

        return ResponseEntity.ok(createdCategory);
    }

    @PutMapping
    public ResponseEntity<CategoryDto> updateCategory(
            @RequestBody CategoryDto categoryDto) {

        CategoryDto updatedCategory = categoryService.update(categoryDto);

        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {

        categoryService.delete(id);

        return ResponseEntity.ok("Category deleted successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {

        CategoryDto category = categoryService.getCategoryById(id);

        return ResponseEntity.ok(category);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {

        List<CategoryDto> categories = categoryService.getAllCategory();

        return ResponseEntity.ok(categories);
    }
}