package com.ecom.product.repository;

import com.ecom.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByProductCategory_Id(Long categoryId, Pageable pageable);
    Page<Product> findByPriceBetween(Double min, Double max, Pageable pageable);
    Page<Product> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

    @Query("SELECT p FROM Product p " +
            "WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.productDescription) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Product> searchProducts(@Param("keyword") String keyword, Pageable pageable);

}
