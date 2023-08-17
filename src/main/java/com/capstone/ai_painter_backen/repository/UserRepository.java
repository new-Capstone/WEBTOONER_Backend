package com.capstone.ai_painter_backen.repository;

import com.capstone.ai_painter_backen.constant.SocialType;
import com.capstone.ai_painter_backen.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Override
    @Query("select u from UserEntity u join fetch u.tutorEntity join fetch u.tuteeEntity where u.id = :id")
    Optional<UserEntity> findById(@Param("id") Long id);


//    public void deleteByLoginId(String loginid);

    boolean existsByUserEmail(String userEmail);

//    Optional<UserEntity> findByLoginId(String loginId);

    @Query("select u from UserEntity u join fetch u.tutorEntity join fetch u.tuteeEntity where u.userEmail = :userEmail")
    Optional<UserEntity> findByUserEmail(@Param("userEmail") String userEmail);//반드시 UserEntity 를 반환할것 왜냐하면 userDetails 를 구현해서 securitydp ㅓㅈㄱ용이 가능하ㄹ

    @Query("select u from UserEntity u join fetch u.tutorEntity join fetch u.tuteeEntity where u.socialType = :socialType and u.socialId = :socialId")
    Optional<UserEntity> findBySocialTypeAndSocialId(@Param("socialType") SocialType socialType, @Param("socialId") String socialId);

    @Query("select u from UserEntity u join fetch u.tutorEntity join fetch u.tuteeEntity where u.refreshToken = :refreshToken")
    Optional<UserEntity> findByRefreshToken(@Param("refreshToken") String refreshToken);

}
