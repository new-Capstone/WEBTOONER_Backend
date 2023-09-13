package com.capstone.ai_painter_backen.controller.Message;

import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.domain.message.MessageEntity;
import com.capstone.ai_painter_backen.domain.message.RoomEntity;
import com.capstone.ai_painter_backen.dto.Result;
import com.capstone.ai_painter_backen.repository.UserRepository;
import com.capstone.ai_painter_backen.repository.message.MessageRepository;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
class MessageControllerTest {

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

    private Long roomId;


    @BeforeEach
    void setUp() {
        UserEntity owner = UserEntity.createUserEssential(UUID.randomUUID().toString(), "qwer", UUID.randomUUID().toString());
        UserEntity visitor = UserEntity.createUserEssential(UUID.randomUUID().toString(), "asdf", UUID.randomUUID().toString());
        userRepository.save(visitor);
        userRepository.save(owner);

        RoomEntity roomEntity = getRoomEntity(owner, visitor);
        roomRepository.save(roomEntity);

        roomId = roomEntity.getId();

        for (int i = 0;i < 20; i++) {
            MessageEntity messageEntity = getMessageEntity(owner, roomEntity);
            messageRepository.save(messageEntity);
        }
    }

    @AfterEach
    void after() {
        messageRepository.deleteAll();
        roomRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void 메시지_모두조회() throws Exception{
        //given
        int page = 0;
        int size = 10;

        String url = "http://localhost:" + port + "/message" + "/" + roomId + "?page=" + page + "&size=" + size;

        //when
        long startTime = System.currentTimeMillis();

        ResponseEntity<Result> responseEntity = restTemplate.getForEntity(url, Result.class);

        long endTime = System.currentTimeMillis();
        log.info("getMessages api : {}", endTime - startTime);

        //then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody().getSize()).isEqualTo(10);
    }

    private RoomEntity getRoomEntity(UserEntity owner, UserEntity visitor) {
        RoomEntity roomEntity = RoomEntity.builder()
                .owner(owner)
                .visitor(visitor)
                .build();
        return roomEntity;
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