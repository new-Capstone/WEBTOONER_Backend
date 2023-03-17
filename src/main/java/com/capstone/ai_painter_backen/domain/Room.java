package com.capstone.ai_painter_backen.domain;

import jakarta.persistence.*;
import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Room extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visitor_id")
    private User visitor;

    @OneToMany(mappedBy = "room")
    private List<Message> messages = new ArrayList<>();
}
