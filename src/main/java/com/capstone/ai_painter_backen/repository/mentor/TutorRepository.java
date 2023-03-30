package com.capstone.ai_painter_backen.repository.mentor;

import com.capstone.ai_painter_backen.domain.mentor.TutorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.beans.JavaBean;

public interface TutorRepository extends JpaRepository<TutorEntity,Long> {

}
