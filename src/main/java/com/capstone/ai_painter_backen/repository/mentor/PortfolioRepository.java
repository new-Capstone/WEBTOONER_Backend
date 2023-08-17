package com.capstone.ai_painter_backen.repository.mentor;

import com.capstone.ai_painter_backen.domain.mentor.PortfolioEntity;
import com.capstone.ai_painter_backen.domain.mentor.TutorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<PortfolioEntity, Long> {
    @Override
    @Query("select distinct p from PortfolioEntity p join fetch p.tutorEntity join fetch p.category where p.id = :id")
    Optional<PortfolioEntity> findById(@Param("id") Long id);

    @Query("select distinct p from PortfolioEntity p join fetch p.tutorEntity join fetch p.category where p.tutorEntity = :tutor")
    List<PortfolioEntity> findAllByTutorEntity(@Param("tutor") TutorEntity tutor);

}
