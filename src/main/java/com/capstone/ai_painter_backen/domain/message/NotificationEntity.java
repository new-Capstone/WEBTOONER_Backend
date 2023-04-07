package com.capstone.ai_painter_backen.domain.message;

import com.capstone.ai_painter_backen.domain.BaseEntity;
import com.capstone.ai_painter_backen.domain.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEntity extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String link;
    private boolean checked;

    @OneToOne(fetch = FetchType.LAZY)
    private MessageEntity message;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    public void setUser(UserEntity userEntity) {
        this.user = userEntity;
    }

    //알림 확인
    public void check() {
        this.checked = true;
    }
}
