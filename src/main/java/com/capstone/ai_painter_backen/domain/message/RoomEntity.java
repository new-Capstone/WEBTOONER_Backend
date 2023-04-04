package com.capstone.ai_painter_backen.domain.message;

import com.capstone.ai_painter_backen.domain.BaseEntity;
import com.capstone.ai_painter_backen.domain.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomEntity extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private UserEntity owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visitor_id")
    private UserEntity visitor;

    @OneToMany(mappedBy = "roomEntity", cascade = CascadeType.ALL)
    private List<MessageEntity> messageEntities = new ArrayList<>();


    public void setOwner(UserEntity userEntity) {
        this.owner = userEntity;
    }

    public void setVisitor(UserEntity userEntity) {
        this.visitor = userEntity;
    }
}
