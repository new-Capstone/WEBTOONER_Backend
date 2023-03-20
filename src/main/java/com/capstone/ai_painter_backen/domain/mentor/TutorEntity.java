package com.capstone.ai_painter_backen.domain.mentor;

import com.capstone.ai_painter_backen.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
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
}
