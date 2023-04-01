package com.capstone.ai_painter_backen.domain.mentor;

import com.capstone.ai_painter_backen.domain.BaseEntity;
import com.capstone.ai_painter_backen.domain.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TutorEntity extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @OneToMany(mappedBy = "tutorEntity")
    private List<TuteeEntity> tuteeEntities = new ArrayList<>();

    @OneToMany(mappedBy = "tutorEntity")
    private List<CategoryTutorEntity> categoryTutorEntities = new ArrayList<>();

    @OneToOne(mappedBy = "tutorEntity", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private UserEntity userEntity;


    public void update(String description, List<CategoryTutorEntity> categoryTutorEntities){//변경 메소드 작성
        this.description = description;
        this.categoryTutorEntities = categoryTutorEntities;
    }
}
