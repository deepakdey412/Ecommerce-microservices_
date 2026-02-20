package com.ecom.product.repository;

import com.ecom.product.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository  extends JpaRepository<Category,Long> {
    List<Category> findByProductId(Long id);
    boolean existsByName(String name);
}
