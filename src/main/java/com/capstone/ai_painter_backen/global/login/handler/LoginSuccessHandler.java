package com.capstone.ai_painter_backen.global.login.handler;


import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.dto.UserDto;
import com.capstone.ai_painter_backen.global.jwt.service.JwtService;
import com.capstone.ai_painter_backen.mapper.UserMapper;
import com.capstone.ai_painter_backen.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;


@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    private final UserMapper userMapper;
    private final ObjectMapper objectMapper;

    @Value("${jwt.access.expiration}")
    private String accessTokenExpiration;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        String email = extractUsername(authentication); // 인증 정보에서 Username(email) 추출
        String accessToken = jwtService.createAccessToken(email); // JwtService의 createAccessToken을 사용하여 AccessToken 발급
        String refreshToken = jwtService.createRefreshToken(); // JwtService의 createRefreshToken을 사용하여 RefreshToken 발급

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken); // 응답 헤더에 AccessToken, RefreshToken 실어서 응답

        //redirect to root url (https://capstone-webtooner.com)
//        response.sendRedirect("/");

        UserEntity userEntity = userRepository.findByUserEmail(email).orElseThrow(()-> new UsernameNotFoundException("no user"));
        userEntity.updateRefreshToken(refreshToken);
        userRepository.saveAndFlush(userEntity);
        //content type config
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        UserDto.UserResponseDto userResponseDto = userMapper.userEntityToUserResponseDto(userEntity);
        response.getWriter().write(objectMapper.writeValueAsString(userResponseDto));


//                .ifPresent(user -> {
//                    user.updateRefreshToken(refreshToken);
//                    userRepository.saveAndFlush(user);
//                });



        log.info("로그인에 성공하였습니다. 이메일 : {}", email);
        log.info("로그인에 성공하였습니다. AccessToken : {}", accessToken);
        log.info("발급된 AccessToken 만료 기간 : {}", accessTokenExpiration);
    }

    private String extractUsername(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
}
