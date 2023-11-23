package com.elkastali.productservice;

import com.elkastali.productservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestH2Repository  extends JpaRepository<Product,Long> {
}
