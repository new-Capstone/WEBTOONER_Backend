package com.capstone.ai_painter_backen.domain.image;

import com.capstone.ai_painter_backen.domain.BaseEntity;
import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.domain.image.AfterImageEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeforeImageEntity extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    String beforeImageUri;//todo 협의 변환전의 이미지를 s3에 올릴려면 필요함... 아니야 사실 그냥 넣고 싶은듯

    @OneToMany(mappedBy = "beforeImageEntity")
    private List<AfterImageEntity> afterImageEntities = new ArrayList<>();
}
