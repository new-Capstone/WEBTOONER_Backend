package com.capstone.ai_painter_backen.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class AfterImage extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageURI;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "before_image_id")
    private BeforeImage beforeImage;
}
