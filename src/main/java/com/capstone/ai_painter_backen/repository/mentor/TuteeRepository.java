package com.capstone.ai_painter_backen.repository.mentor;

import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.domain.mentor.TuteeEntity;
import com.capstone.ai_painter_backen.domain.mentor.TutorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface TuteeRepository extends JpaRepository<TuteeEntity, Long> {
    @Override
    @Query("select distinct t from TuteeEntity t join fetch t.tutorEntity join fetch t.userEntity where t.Id = :id")
    Optional<TuteeEntity> findById(@Param("id") Long id);

    TutorEntity findByUserEntity_Id(Long userId);

    @Query(value = "select t from TuteeEntity t join fetch t.tutorEntity join fetch t.userEntity where t.tutorEntity.id = :tutorId")
    Page<TuteeEntity> findAllByTutorEntityIdWithPagination(@Param("tutorId") Long tutorId, Pageable pageable);
}
