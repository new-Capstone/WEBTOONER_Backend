package com.capstone.ai_painter_backen.controller.auth.authenticationservice;

import com.capstone.ai_painter_backen.constant.Role;
import com.capstone.ai_painter_backen.controller.auth.AuthenticationRequest;
import com.capstone.ai_painter_backen.controller.auth.AuthenticationResponse;
import com.capstone.ai_painter_backen.controller.auth.RegisterRequest;
import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.exception.BusinessLogicException;
import com.capstone.ai_painter_backen.exception.ExceptionCode;
import com.capstone.ai_painter_backen.repository.UserRepository;
import com.capstone.ai_painter_backen.securiry.config.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(@Valid RegisterRequest request){
        var user = UserEntity.builder()
                .userEmail(request.getEmail())
                .userRealName(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().
                            token(jwtToken)
                        .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );
        log.error(authenticationRequest.getEmail());

        var user = repository.findByUserEmail(authenticationRequest.getEmail())
                .orElseThrow( () -> new BusinessLogicException(ExceptionCode.UNAUTHORIZED_MEMBER));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
