package com.capstone.ai_painter_backen.domain.message;

import com.capstone.ai_painter_backen.domain.BaseEntity;
import com.capstone.ai_painter_backen.domain.UserEntity;
import jakarta.persistence.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageEntity extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private RoomEntity roomEntity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_user_id")
    private UserEntity chatUserEntity;

    //연관관계 편의 메서드
    public void addRoomEntity(RoomEntity roomEntity) {
        this.roomEntity = roomEntity;
        roomEntity.getMessageEntities().add(this);
    }

    public void setChatUserEntity(UserEntity userEntity) {
        this.chatUserEntity = userEntity;
    }
}
