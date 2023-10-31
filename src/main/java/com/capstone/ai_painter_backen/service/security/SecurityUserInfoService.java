package com.capstone.ai_painter_backen.service.security;

import com.capstone.ai_painter_backen.dto.UserDto;
import com.capstone.ai_painter_backen.exception.BusinessLogicException;
import com.capstone.ai_painter_backen.exception.ExceptionCode;
import com.capstone.ai_painter_backen.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class SecurityUserInfoService {

    private final UserRepository userRepository;

    UserDto.CusTomUserPrincipalDto getUserInfoFromSecurityContextHolder(){
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
}
