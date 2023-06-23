package com.bikked.repository;

import com.bikked.dto.CategoryDto;
import com.bikked.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    List<Category> findByTitleContaining(String keyword);

    Category findByTitle(String title);

}
