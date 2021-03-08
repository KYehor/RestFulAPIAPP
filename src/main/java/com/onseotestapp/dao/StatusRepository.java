package com.onseotestapp.dao;

import com.onseotestapp.domain.Product;
import com.onseotestapp.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
}
