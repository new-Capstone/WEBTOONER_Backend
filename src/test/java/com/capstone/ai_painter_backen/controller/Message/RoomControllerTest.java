package com.capstone.ai_painter_backen.controller.Message;

import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.domain.message.RoomEntity;
import com.capstone.ai_painter_backen.dto.Message.RoomDto;
import com.capstone.ai_painter_backen.repository.UserRepository;
import com.capstone.ai_painter_backen.repository.message.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
class RoomControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoomRepository roomRepository;
    private RoomDto.RoomResponseDto roomResponseDto;

    @BeforeEach
    void setUp() {
        UserEntity owner = UserEntity.createUserEssential(UUID.randomUUID().toString(), "qwer", UUID.randomUUID().toString());
        UserEntity visitor = UserEntity.createUserEssential(UUID.randomUUID().toString(), "asdf", UUID.randomUUID().toString());
        userRepository.save(owner);
        userRepository.save(visitor);
        roomResponseDto = RoomDto.RoomResponseDto.builder()
                .ownerId(owner.getId())
                .visitorId(visitor.getId())
                .build();
    }

    @AfterEach
    void after() {
        roomRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void 룸생성() {
        //given
        RoomDto.RoomPostDto roomPostDto = RoomDto.RoomPostDto.builder()
                .ownerId(roomResponseDto.getOwnerId())
                .visitorId(roomResponseDto.getVisitorId())
                .build();

        String url = "http://localhost:" + port + "/room/new";

        //when
        long startTime = System.currentTimeMillis();

        ResponseEntity<RoomDto.RoomResponseDto> responseEntity = restTemplate.postForEntity(url, roomPostDto, RoomDto.RoomResponseDto.class);

        long endTime = System.currentTimeMillis();
        log.info("createRoom api : {}", endTime - startTime);
        //then
        Assertions.assertThat(HttpStatus.OK).isEqualTo(responseEntity.getStatusCode());
    }

    @Test
    void 룸삭제() {
        //given
        RoomEntity roomEntity = getRoomEntity();
        roomRepository.save(roomEntity);

        String url = "http://localhost:" + port + "/room/delete";
        RoomDto.RoomDeleteDto roomDeleteDto = RoomDto.RoomDeleteDto.builder().roomId(roomEntity.getId()).build();

        //when
        long startTime = System.currentTimeMillis();

        ResponseEntity<Void> responseEntity = restTemplate.exchange(
                url, HttpMethod.DELETE, new HttpEntity<>(roomDeleteDto), Void.class);

        long endTime = System.currentTimeMillis();
        log.info("deleteRoom api : {}", endTime - startTime);

        //then
        Assertions.assertThat(HttpStatus.OK).isEqualTo(responseEntity.getStatusCode());
    }

    @Test
    void 룸조회() {
        //given
        RoomEntity roomEntity = getRoomEntity();
        roomRepository.save(roomEntity);
        String url = "http://localhost:" + port + "/room?roomId=" + roomEntity.getId();

        //when
        long startTime = System.currentTimeMillis();

        ResponseEntity<RoomDto.RoomResponseDto> responseEntity = restTemplate
                .getForEntity(url, RoomDto.RoomResponseDto.class);

        long endTime = System.currentTimeMillis();
        log.info("retrieveRoom api : {}", endTime - startTime);

        //then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("userId로 룸 조회")
    void 유저_아이디로_룸조회() {
        //given
        UserEntity owner = userRepository.findById(roomResponseDto.getOwnerId()).get();
        for (int i = 0; i < 20; i++) {
            UserEntity visitor = UserEntity.createUserEssential(UUID.randomUUID().toString(), "qwer", UUID.randomUUID().toString());
            userRepository.save(visitor);

            RoomEntity roomEntity = RoomEntity.builder()
                    .owner(owner)
                    .visitor(visitor)
                    .build();
            roomRepository.save(roomEntity);
        }

        String url = "http://localhost:" + port + "/room" + "/" + owner.getId();

        //when
        long startTime = System.currentTimeMillis();

        ResponseEntity<List<RoomDto.RoomResponseDto>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<RoomDto.RoomResponseDto>>() {}
        );

        long endTime = System.currentTimeMillis();
        log.info("retrieveRooms api : {}", endTime - startTime);

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody().size()).isEqualTo(20);
    }

    private RoomEntity getRoomEntity() {
        UserEntity owner = userRepository.findById(roomResponseDto.getOwnerId()).get();
        UserEntity visitor = userRepository.findById(roomResponseDto.getVisitorId()).get();
        RoomEntity roomEntity = RoomEntity.builder()
                .visitor(visitor)
                .owner(owner)
                .build();
        return roomEntity;
    }
}