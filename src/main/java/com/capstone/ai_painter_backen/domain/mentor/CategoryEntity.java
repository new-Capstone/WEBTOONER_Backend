package com.capstone.ai_painter_backen.domain.mentor;

import com.capstone.ai_painter_backen.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class CategoryEntity extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoryName;

    @OneToMany(mappedBy = "categoryEntity")
    private List<CategoryTutorEntity> categoryTutorEntities = new ArrayList<>();
}
