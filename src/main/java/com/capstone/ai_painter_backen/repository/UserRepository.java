package com.capstone.ai_painter_backen.repository;

import com.capstone.ai_painter_backen.constant.SocialType;
import com.capstone.ai_painter_backen.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long>, UserRepositoryCustom{
//    public void deleteByLoginId(String loginid);

    boolean existsByUserEmail(String userEmail);

//    Optional<UserEntity> findByLoginId(String loginId);
    Optional<UserEntity> findByUserEmail(String userEmail);//반드시 UserEntity 를 반환할것 왜냐하면 userDetails 를 구현해서 securitydp ㅓㅈㄱ용이 가능하ㄹ

    Optional<UserEntity> findBySocialTypeAndSocialId(SocialType socialType, String socialId);

    Optional<UserEntity> findByRefreshToken(String refreshToken);

    @Query("SELECT user.id from UserEntity user where user.userEmail = ?1")
    Optional<Long> findUserIdByUserEmail(String email);

}
