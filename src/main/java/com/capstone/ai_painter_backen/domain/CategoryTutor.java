package com.capstone.ai_painter_backen.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class CategoryTutor extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tutor_id")
    private Tutor tutor;
}
