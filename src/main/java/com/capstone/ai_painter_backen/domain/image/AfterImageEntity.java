package com.capstone.ai_painter_backen.domain.image;

import com.capstone.ai_painter_backen.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class AfterImageEntity extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageURI;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "before_image_id")
    private BeforeImageEntity beforeImageEntity;
}
