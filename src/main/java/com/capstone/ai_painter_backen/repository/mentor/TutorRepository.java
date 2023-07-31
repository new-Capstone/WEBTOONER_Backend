package com.capstone.ai_painter_backen.repository.mentor;

import com.capstone.ai_painter_backen.domain.mentor.TutorEntity;
import com.capstone.ai_painter_backen.dto.mentor.TutorDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.beans.JavaBean;
import java.util.List;

public interface TutorRepository extends JpaRepository<TutorEntity,Long> {

    TutorEntity findByUserEntity_Id(Long userId);


    @Query("select distinct t from TutorEntity t " +
            "join fetch t.categoryTutorEntities te join fetch te.categoryEntity c where c.categoryName = :categoryName")
    List<TutorEntity> findAllByCategoryName(String categoryName);
}
