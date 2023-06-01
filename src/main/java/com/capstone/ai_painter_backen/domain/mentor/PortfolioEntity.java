package com.capstone.ai_painter_backen.domain.mentor;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class PortfolioEntity { //todo 삭제할 때 s3 사진도 삭제 가능하게 만들것.

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    String imageUri;

    @ManyToOne(fetch = FetchType.LAZY)
    TutorEntity tutorEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    CategoryEntity category;

    public void setImageUri(String imageUri){
        this.imageUri = imageUri;
    }

    public void setTutorEntity(TutorEntity tutor){
        this.tutorEntity  = tutor;
    }
    public void setTutorNull(){this.tutorEntity = null;}

    public void setCategory(CategoryEntity categoryEntity){
        this.category = categoryEntity;
    }

    public void delete(){
        this.category = null;
        this.tutorEntity = null;
    }


}
