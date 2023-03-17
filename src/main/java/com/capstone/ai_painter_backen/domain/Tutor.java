package com.capstone.ai_painter_backen.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Tutor extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @OneToMany(mappedBy = "tutor")
    private List<Tutee> tutees = new ArrayList<>();

    @OneToMany(mappedBy = "tutor")
    private List<CategoryTutor> categoryTutors = new ArrayList<>();
}
