package com.capstone.ai_painter_backen.domain;

import com.capstone.ai_painter_backen.domain.mentor.TuteeEntity;
import com.capstone.ai_painter_backen.domain.mentor.TutorEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class UserEntity extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String loginId;
    private String password;
    private String description;
    private String profileImage;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tutor_id")
    private TutorEntity tutorEntity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tutee_id")
    private TuteeEntity tuteeEntity;
}
