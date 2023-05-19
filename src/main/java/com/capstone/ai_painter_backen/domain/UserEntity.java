package com.capstone.ai_painter_backen.domain;

import com.capstone.ai_painter_backen.constant.SocialType;
import com.capstone.ai_painter_backen.constant.Role;
import com.capstone.ai_painter_backen.domain.mentor.TuteeEntity;
import com.capstone.ai_painter_backen.domain.mentor.TutorEntity;
import com.capstone.ai_painter_backen.dto.UserDto;
import com.capstone.ai_painter_backen.dto.image.S3ImageInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(
        uniqueConstraints = @UniqueConstraint(
                name = "user_email",
                columnNames = "user_email"
        )
)
public class UserEntity extends BaseEntity implements UserDetails{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userRealName;
    @Column(name = "user_email")
    private String userEmail;

    private String password;
    private String description;
    private String profileUri;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tutor_id")
    private TutorEntity tutorEntity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tutee_id")
    private TuteeEntity tuteeEntity;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private SocialType socialType; // KAKAO, NAVER, GOOGLE

    private String socialId; // 로그인한 소셜 타입의 식별자 값 (일반 로그인인 경우 null)

    private String refreshToken; // 리프레시 토큰

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

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username='" + userEmail + '\'' + ", password='" + password + '\'' + ", authorities="
                + role + '}';
    }

    @Override
    public String getUsername() {
        return this.userEmail;
    }
    @Override
    public String getPassword() {
        return password;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }
    // 유저 권한 설정 메소드
    public void authorizeUser() {

        this.role = Role.USER;
    }

    public void userLogOut(){
        this.refreshToken= null;
    }

}
