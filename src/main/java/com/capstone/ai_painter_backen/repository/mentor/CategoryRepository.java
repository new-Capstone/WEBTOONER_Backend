package com.capstone.ai_painter_backen.repository.mentor;

import com.capstone.ai_painter_backen.domain.mentor.CategoryEntity;
import com.capstone.ai_painter_backen.domain.mentor.CategoryTutorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    boolean existsByCategoryName(String name);

    CategoryEntity findCategoryEntityByCategoryName(String categoryName);

    @Query("select c from CategoryEntity c where c.categoryName in :categoryNames")
    List<CategoryEntity> findCategoryInNames(List<String> categoryNames);
}
