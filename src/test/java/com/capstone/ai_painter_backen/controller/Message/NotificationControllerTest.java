package com.capstone.ai_painter_backen.controller.Message;

import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.domain.message.MessageEntity;
import com.capstone.ai_painter_backen.domain.message.NotificationEntity;
import com.capstone.ai_painter_backen.domain.message.RoomEntity;
import com.capstone.ai_painter_backen.dto.Message.NotificationDto;
import com.capstone.ai_painter_backen.repository.UserRepository;
import com.capstone.ai_painter_backen.repository.message.MessageRepository;
import com.capstone.ai_painter_backen.repository.message.NotificationRepository;
import com.capstone.ai_painter_backen.repository.message.RoomRepository;
import lombok.extern.slf4j.Slf4j;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
class NotificationControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    private Long visitorId;

    @BeforeEach
    void setUp() {
        UserEntity owner = UserEntity.createUserEssential(UUID.randomUUID().toString(), "qwer", UUID.randomUUID().toString());
        UserEntity visitor = UserEntity.createUserEssential(UUID.randomUUID().toString(), "asdf", UUID.randomUUID().toString());
        userRepository.save(visitor);
        userRepository.save(owner);

        RoomEntity roomEntity = getRoomEntity(owner, visitor);
        roomRepository.save(roomEntity);

        for (int i = 0; i < 20; i++) {
            MessageEntity messageEntity = getMessageEntity(owner, roomEntity);

            messageRepository.save(messageEntity);
            NotificationEntity notificationEntity = getNotificationEntity(visitor, messageEntity);
            visitorId = visitor.getId();
            notificationRepository.save(notificationEntity);
        }
    }

    @AfterEach
    void after() {
        notificationRepository.deleteAll();
        messageRepository.deleteAll();
        roomRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void 알림_조회() {
        //given
        Long userId = visitorId;
        String url = "http://localhost:" + port + "/notification?userId=" + userId;

        //when
        long startTime = System.currentTimeMillis();

        ResponseEntity<List<NotificationDto.NotificationResponseDto>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                null,
                new ParameterizedTypeReference<List<NotificationDto.NotificationResponseDto>>() {}
        );

        long endTime = System.currentTimeMillis();
        log.info("retrieve Notification api : {}", endTime - startTime);

        log.info(": {}", responseEntity.getBody());

        //then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody().size()).isEqualTo(20);
    }

    @Test
    void 알림_삭제() {
        //given
        Long userId = visitorId;
        String url = "http://localhost:" + port + "/notification?userId=" + userId;

        //when
        long startTime = System.currentTimeMillis();

        ResponseEntity<Void> responseEntity = restTemplate.exchange(
                url, HttpMethod.DELETE, null, Void.class);

        long endTime = System.currentTimeMillis();
        log.info("delete Notification api : {}", endTime - startTime);

        //then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private RoomEntity getRoomEntity(UserEntity owner, UserEntity visitor) {
        RoomEntity roomEntity = RoomEntity.builder()
                .owner(owner)
                .visitor(visitor)
                .build();
        return roomEntity;
    }

    private NotificationEntity getNotificationEntity(UserEntity visitor, MessageEntity messageEntity) {
        NotificationEntity notificationEntity = NotificationEntity.builder()
                .checked(Boolean.FALSE)
                .message(messageEntity)
                .user(visitor)
                .build();
        return notificationEntity;
    }

    private MessageEntity getMessageEntity(UserEntity owner, RoomEntity roomEntity) {
        MessageEntity messageEntity = MessageEntity.builder()
                .content("hello")
                .roomEntity(roomEntity)
                .chatUserEntity(owner)
                .build();
        return messageEntity;
    }
}