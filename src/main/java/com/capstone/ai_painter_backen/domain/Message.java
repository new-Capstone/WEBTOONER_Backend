package com.capstone.ai_painter_backen.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Message extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_user_id")
    private User chatUser;
}
