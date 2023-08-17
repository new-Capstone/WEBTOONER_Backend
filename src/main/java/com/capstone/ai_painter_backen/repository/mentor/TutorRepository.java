package com.capstone.ai_painter_backen.repository.mentor;

import com.capstone.ai_painter_backen.domain.mentor.TutorEntity;
import com.capstone.ai_painter_backen.dto.mentor.TutorDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.beans.JavaBean;
import java.util.List;
import java.util.Optional;

public interface TutorRepository extends JpaRepository<TutorEntity,Long> {
    @Override
    @Query("select t from TutorEntity t join fetch t.userEntity join fetch t.categoryTutorEntities " +
            "join fetch t.portfolioEntities join fetch t.tuteeEntities where t.id = :id")
    Optional<TutorEntity> findById(@Param("id") Long id);

    TutorEntity findByUserEntity_Id(Long userId);


    @Query("select distinct t from TutorEntity t " +
            "join fetch t.categoryTutorEntities te join fetch te.categoryEntity c where c.categoryName = :categoryName")
    List<TutorEntity> findAllByCategoryName(String categoryName);
}
