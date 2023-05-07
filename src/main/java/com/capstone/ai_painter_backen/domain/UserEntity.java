package com.capstone.ai_painter_backen.domain;

import com.capstone.ai_painter_backen.domain.mentor.TuteeEntity;
import com.capstone.ai_painter_backen.domain.mentor.TutorEntity;
import com.capstone.ai_painter_backen.dto.UserDto;
import com.capstone.ai_painter_backen.dto.image.S3ImageInfo;
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
public class UserEntity extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String loginId;
    private String password;
    private String description;
    private String profileUri;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tutor_id")
    private TutorEntity tutorEntity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tutee_id")
    private TuteeEntity tuteeEntity;

    //==갱신 로직==//
    public void update(UserDto.UserPatchDto patchDto, S3ImageInfo s3ImageInfo){
        this.password = patchDto.getPassword();
        this.description = patchDto.getDescription();
        this.profileUri = s3ImageInfo.getFileURI();

    }

    //==TutorEnroll==//
    public void enrollTutor(TutorEntity tutorEntity){
        this.tutorEntity = tutorEntity;
    }
    public void unrollTutor(){this.tutorEntity = null;}

    //==TutorEnroll==//
    public void enrollTutee(TuteeEntity tuteeEntity){this.tuteeEntity = tuteeEntity;}
    public void unrollTutee(){this.tuteeEntity = null;}



}
