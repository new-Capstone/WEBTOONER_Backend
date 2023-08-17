package com.capstone.ai_painter_backen.repository.mentor;

import com.capstone.ai_painter_backen.domain.mentor.CategoryTutorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryTutorRepository extends JpaRepository<CategoryTutorEntity, Long> {
    @Override
    @Query("select distinct c from CategoryTutorEntity c join fetch c.tutorEntity join fetch c.categoryEntity where c.id = :id")
    Optional<CategoryTutorEntity> findById(@Param("id") Long id);
}
