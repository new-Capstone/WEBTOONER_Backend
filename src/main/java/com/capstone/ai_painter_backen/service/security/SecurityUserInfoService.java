package com.capstone.ai_painter_backen.service.security;

import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.dto.UserDto;
import com.capstone.ai_painter_backen.exception.BusinessLogicException;
import com.capstone.ai_painter_backen.exception.ExceptionCode;
import com.capstone.ai_painter_backen.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SecurityUserInfoService {

    private final UserRepository userRepository;

    //default -> public

    public UserDto.CusTomUserPrincipalDto getUserInfoFromSecurityContextHolder(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        UserDto.CusTomUserPrincipalDto cusTomUserPrincipalDto = UserDto.CusTomUserPrincipalDto.builder()
                .userId(null)
                .email(userDetails.getUsername())
                .build();
        userRepository.findUserIdByUserEmail(cusTomUserPrincipalDto.getEmail())
                .orElseThrow(()-> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        return cusTomUserPrincipalDto;
    }

    /**
     * loginUser를 가져올 때, dto에 담아서 넘겨주는데, 실제 사용할 떄, email로 user를 다시 가져와야 해서 조회가 2번 발생함.
     * 그냥 바로 UserEntity 던져주는 게 더 좋을 듯
     * 일단은 getUserInfoFromSecurityContextHolder 메소드 사용해서 했음.
     */
    public UserEntity getLoginUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new BusinessLogicException(ExceptionCode.INVALID_AUTH_TOKEN);
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        UserEntity userEntity = userRepository.findByUserEmail(userDetails.getUsername()).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        return userEntity;
    }
}
