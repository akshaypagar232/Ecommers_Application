package com.bikked.repository;

import com.bikked.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    Page<Product> findByTitleContaining(String title,Pageable pageable);
    Page<Product> findByLiveTrue(Pageable pageable);

}
