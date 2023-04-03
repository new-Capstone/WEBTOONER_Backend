package com.capstone.ai_painter_backen.repository.mentor;

import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.domain.mentor.TuteeEntity;
import com.capstone.ai_painter_backen.domain.mentor.TutorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TuteeRepository extends JpaRepository<TuteeEntity, Long> {

    TutorEntity findByUserEntity_Id(Long userId);
}
