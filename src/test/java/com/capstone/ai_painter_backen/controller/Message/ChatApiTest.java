package com.capstone.ai_painter_backen.controller.Message;

import com.capstone.ai_painter_backen.constant.MessageType;
import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.domain.message.RoomEntity;
import com.capstone.ai_painter_backen.dto.Message.MessageDto;
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
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class ChatApiTest {

    @LocalServerPort
    private int port;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private MessageRepository messageRepository;

    @BeforeEach
    void setUp() {
        UserEntity owner = UserEntity.createUserEssential(UUID.randomUUID().toString(), "qwer", UUID.randomUUID().toString());
        UserEntity visitor = UserEntity.createUserEssential(UUID.randomUUID().toString(), "asdf", UUID.randomUUID().toString());
        userRepository.save(owner);
        userRepository.save(visitor);

        RoomEntity roomEntity = getRoomEntity(owner, visitor);
        roomRepository.save(roomEntity);
    }

    @AfterEach
    void after() {
        notificationRepository.deleteAll();
        messageRepository.deleteAll();
        roomRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void 채팅() throws Exception {
        Long roomId = 1L;

        WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(getTransports()));

        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        String url = "ws://localhost:" + port + "/ws-stomp";

        StompSession stompSession = stompClient.connect(url, new StompSessionHandlerAdapter() {
        }).get(5, TimeUnit.SECONDS);


        BlockingQueue<MessageDto.MessagePostDto> blockingQueue = new LinkedBlockingQueue<>();

        stompSession.subscribe("/sub/chat/room/" + roomId, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return MessageDto.MessagePostDto.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                blockingQueue.offer((MessageDto.MessagePostDto) payload);
            }
        });

        MessageDto.MessagePostDto messagePostDto = new MessageDto.MessagePostDto();
        messagePostDto.setChatUserEntityId(1L);
        messagePostDto.setRoomEntityId(1L);
        messagePostDto.setWriter("testUser");
        messagePostDto.setType(MessageType.TALK);
        messagePostDto.setContent("hello");

        stompSession.send("/pub/chat/message", messagePostDto);

        MessageDto.MessagePostDto receivedMessage = blockingQueue.poll(5, TimeUnit.SECONDS);
        Assertions.assertThat(receivedMessage.getContent()).isEqualTo("hello");

        stompSession.disconnect();
    }

    private List<Transport> getTransports() {
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        return transports;
    }

    private RoomEntity getRoomEntity(UserEntity owner, UserEntity visitor) {
        RoomEntity roomEntity = RoomEntity.builder()
                .visitor(visitor)
                .owner(owner)
                .build();
        return roomEntity;
    }
}
