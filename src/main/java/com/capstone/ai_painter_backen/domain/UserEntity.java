package com.capstone.ai_painter_backen.domain;

import com.capstone.ai_painter_backen.constant.Role;
import com.capstone.ai_painter_backen.domain.mentor.TuteeEntity;
import com.capstone.ai_painter_backen.domain.mentor.TutorEntity;
import com.capstone.ai_painter_backen.dto.UserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
    private String profileImage;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tutor_id")
    private TutorEntity tutorEntity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tutee_id")
    private TuteeEntity tuteeEntity;

    @Enumerated(EnumType.STRING)
    Role role;

    //==갱신 로직==//
    public void update(UserDto.UserPatchDto patchDto){
        this.password = patchDto.getPassword();
        this.description = patchDto.getDescription();
        this.profileImage = patchDto.getProfileImage();
        this.tutorEntity = patchDto.getTutorEntity();
        this.tuteeEntity = patchDto.getTuteeEntity();
    }

    //==TutorEnroll==//
    public void enrollTutor(TutorEntity tutorEntity){
        this.tutorEntity = tutorEntity;
    }
    public void unrollTutor(){this.tutorEntity = null;}

    //==TutorEnroll==//
    public void enrollTutee(TuteeEntity tuteeEntity){this.tuteeEntity = tuteeEntity;}
    public void unrollTutee(){this.tuteeEntity = null;}

//    @Override
//    public int hashCode() {//hash 를 오버라이딩 해서 user 객체를 비교할 때는 아이디 이름 비번 권한을 비교하게 만듦
//        return Objects.hash(id, userEmail, password, role);
//    }
//    @Override
//    public boolean equals(Object o) {//두 유저를 비교할 때 사용하는 함수
//        if (this == o)
//            return true;
//        if (o == null || getClass() != o.getClass())
//            return false;
//        UserEntity user = (UserEntity) o;
//        return Objects.equals(id, user.id) && Objects.equals(userEmail, user.userEmail)
//                && Objects.equals(password, user.password) && Objects.equals(role, user.role);
//    }
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
}
