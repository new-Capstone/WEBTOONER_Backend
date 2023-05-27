package com.capstone.ai_painter_backen.config;

import com.capstone.ai_painter_backen.global.jwt.service.JwtService;
import com.capstone.ai_painter_backen.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ChatPreHandler implements ChannelInterceptor {

    private final JwtService jwtService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        try {
            StompHeaderAccessor headerAccessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
            String authorizationHeader = String.valueOf(headerAccessor.getNativeHeader("Authorization"));
            StompCommand command = headerAccessor.getCommand();

            if (command.equals(StompCommand.UNSUBSCRIBE) || command.equals(StompCommand.MESSAGE) ||
                    command.equals(StompCommand.CONNECTED) || command.equals(StompCommand.SEND)) {
                return message;
            } else if (command.equals(StompCommand.ERROR)) {
                throw new MessageDeliveryException("error");
            }

            if (authorizationHeader == null) {
                log.info("chat header가 없는 요청입니다.");
                throw new RuntimeException();
            }

            //token 분리
            String token = "";
            String authorizationHeaderStr = authorizationHeader.replace("[", "").replace("]", "");
            log.info("auth : {}", authorizationHeaderStr);
            /*
            if (authorizationHeaderStr.startsWith("Bearer ")) {
                token = authorizationHeaderStr.replace("Bearer ", "");
            } else {
                log.error("Authorization 헤더 형식이 틀립니다. : {}", authorizationHeader);
                throw new RuntimeException();
            }
             */
            boolean isTokenValid = jwtService.isTokenValid(token);

            if (isTokenValid) {
                return message;
            } else {
                throw new RuntimeException();
            }
        } catch (RuntimeException e) {
            log.error("JWT");
            return null; // 임시
        }
    }
}
