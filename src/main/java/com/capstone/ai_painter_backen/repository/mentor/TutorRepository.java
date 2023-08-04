package com.capstone.ai_painter_backen.repository.mentor;

import com.capstone.ai_painter_backen.domain.mentor.TutorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.beans.JavaBean;

public interface TutorRepository extends JpaRepository<TutorEntity,Long> {

    TutorEntity findByUserEntity_Id(Long userId);

}
