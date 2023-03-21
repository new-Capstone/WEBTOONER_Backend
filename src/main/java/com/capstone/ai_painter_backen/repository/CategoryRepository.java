package com.capstone.ai_painter_backen.repository;

import com.capstone.ai_painter_backen.domain.mentor.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    boolean existsByCategoryName(String name);
}
