package com.capstone.ai_painter_backen.domain.mentor;

import com.capstone.ai_painter_backen.domain.BaseEntity;
import jakarta.persistence.*;
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

    private String categoryName;

    @OneToMany(mappedBy = "categoryEntity")
    private List<CategoryTutorEntity> categoryTutorEntities = new ArrayList<>();

    public void update(String CategoryName) {
        this.categoryName = CategoryName;
    }
}

