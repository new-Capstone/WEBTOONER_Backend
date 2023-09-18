package com.capstone.ai_painter_backen.repository.image.afterimage;

import com.capstone.ai_painter_backen.domain.image.AfterImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AfterImageRepository extends JpaRepository<AfterImageEntity, Long>, AfterImageRepositoryCustom {

//    @Query(value = "SELECT DISTINCT a FROM AfterImageEntity a WHERE a.beforeImageEntity.id = ?1",
//            countQuery = "SELECT COUNT(a) FROM AfterImageEntity a WHERE a.id = ?1")
//    List<AfterImageEntity> findAllByBeforeImageEntityId(Long beforeEntityId); //todo 변환해서 한방 쿼리로 작성할 것

//    @Query(value = "SELECT DISTINCT a FROM AfterImageEntity a WHERE a.beforeImageEntity.id IN ?1",
//            countQuery = "SELECT COUNT(a) FROM AfterImageEntity a WHERE a.beforeImageEntity.id IN ?1")
//    Page<AfterImageEntity> findAllByBeforeImageEntityIdes(Long... beforeImageEntityIds);

}


