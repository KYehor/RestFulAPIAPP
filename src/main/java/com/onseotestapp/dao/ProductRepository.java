package com.onseotestapp.dao;

import com.onseotestapp.domain.Product;
import com.onseotestapp.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value="SELECT * FROM Product p left join status s on p.status_id = s.id where p.id like %:keyword% " +
            "or p.name like %:keyword% or s.name like %:keyword%", nativeQuery = true)
    List<Product> findByKeyword(@Param("keyword")String keyword);

    Product findByName(String name);

    @Query(value="SELECT p.id FROM Product p left join status s on p.status_id = s.id where s.name like %:status%", nativeQuery = true)
    List<Long> findByStatus(@Param("status")String status);
}
