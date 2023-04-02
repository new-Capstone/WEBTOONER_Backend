package com.capstone.ai_painter_backen.domain.mentor;

import com.capstone.ai_painter_backen.domain.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.Constraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryEntity extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true) // 유니크 제약 조건
    private String categoryName;

    @OneToMany(mappedBy = "categoryEntity",cascade = CascadeType.PERSIST)
    private List<CategoryTutorEntity> categoryTutorEntities = new ArrayList<>();

    public void update(String CategoryName) {
        this.categoryName = CategoryName;
    }

    public CategoryEntity(String categoryName){
        this.categoryName = categoryName;
    }
}

