package com.capstone.ai_painter_backen.domain.mentor;

import com.capstone.ai_painter_backen.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryTutorEntity extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity categoryEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tutor_id")
    private TutorEntity tutorEntity;

    /*
    생성 메서드 : tutor 생성 시 호출
     */
    
    public static CategoryTutorEntity createCategoryTutor(CategoryEntity category, TutorEntity tutor) {
        CategoryTutorEntity categoryTutor = CategoryTutorEntity.builder()
                .tutorEntity(tutor)
                .categoryEntity(category)
                .build();

        return categoryTutor;
    }
}
