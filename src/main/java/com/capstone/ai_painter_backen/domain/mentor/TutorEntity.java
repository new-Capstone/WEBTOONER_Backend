package com.capstone.ai_painter_backen.domain.mentor;

import com.capstone.ai_painter_backen.domain.BaseEntity;
import com.capstone.ai_painter_backen.domain.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @OneToMany(mappedBy = "tutorEntity", cascade = CascadeType.ALL,orphanRemoval=true)
    private List<CategoryTutorEntity> categoryTutorEntities = new ArrayList<>();

    @OneToOne(mappedBy = "tutorEntity", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private UserEntity userEntity;

    @Column
    @OneToMany(mappedBy = "tutorEntity",cascade = CascadeType.ALL, orphanRemoval=true)
    private List<PortfolioEntity> portfolioEntities = new ArrayList<>();

    @Column
    String tutorName;

    @Column
    String tutorEmail;

    public void update(String description, List<CategoryTutorEntity> categoryTutorEntities){//변경 메소드 작성
        //원래 들어 있던 리스트의 값을 비운 후에 update 를 진행해야 orphand 오류를 피할 수 있다.
        this.description = description;
        for(CategoryTutorEntity categoryTutorEntity: categoryTutorEntities){
            this.categoryTutorEntities.add(categoryTutorEntity);
        }
    }

    public void update(String tutorName, String tutorEmail){//변경 메소드 작성
        this.tutorEmail = tutorEmail;
        this.tutorName = tutorName;
    }
}
