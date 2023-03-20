package com.capstone.ai_painter_backen.domain.mentor;

import com.capstone.ai_painter_backen.domain.BaseEntity;
import com.capstone.ai_painter_backen.domain.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class TuteeEntity extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tutor_id")
    private TutorEntity tutorEntity;

    @OneToOne(mappedBy = "tuteeEntity", fetch = FetchType.LAZY)
    private UserEntity userEntity;
}
