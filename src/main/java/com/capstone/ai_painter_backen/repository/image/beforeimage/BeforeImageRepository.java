package com.capstone.ai_painter_backen.repository.image.beforeimage;

import com.capstone.ai_painter_backen.domain.image.BeforeImageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BeforeImageRepository extends JpaRepository<BeforeImageEntity, Long>, BeforeImageRepositoryCustom{
//    @Query(value = "SELECT b FROM BeforeImageEntity b WHERE b.userEntity.id = ?1",
//            countQuery = "SELECT COUNT(b) FROM BeforeImageEntity b WHERE b.userEntity.id = ?1")
//    Page<BeforeImageEntity> findAllByUserEntityId(Long userId, Pageable pageable);
}


