package com.capstone.ai_painter_backen.service;

import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.dto.UserDto;
import com.capstone.ai_painter_backen.dto.image.S3ImageInfo;
import com.capstone.ai_painter_backen.exception.BusinessLogicException;
import com.capstone.ai_painter_backen.exception.DuplicateNameException;
import com.capstone.ai_painter_backen.exception.ExceptionCode;
import com.capstone.ai_painter_backen.mapper.UserMapper;
import com.capstone.ai_painter_backen.repository.UserRepository;
import com.capstone.ai_painter_backen.service.awsS3.S3FileService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
@Slf4j
@AllArgsConstructor
public class UserService {
    UserMapper userMapper;
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    S3FileService s3FileService;
    private final String DEFAULT_PROFILE = "https://capstone-webtooner.s3.ap-northeast-2.amazonaws.com/a16ce72a-a9ef-448e-842d-bf3e7c948f56_.41.01.png";
    @Transactional
    public UserDto.UserResponseDto createUser(UserDto.UserPostDto userPostDto, MultipartFile profileImage){
        UserEntity userEntity;

        if(profileImage == null){
            userEntity = userMapper.userRequestPostDtoToUserEntity(userPostDto);
            userEntity.setProfileImage(DEFAULT_PROFILE);
        }else{

            S3ImageInfo s3ImageInfo = s3FileService.uploadMultiFile(profileImage);
            userEntity = userMapper.userRequestPostDtoToUserEntity(userPostDto, s3ImageInfo);
        }


        if (userRepository.existsByUserEmail(userEntity.getUserEmail())) {
            throw new DuplicateNameException();
        }

        userEntity.passwordEncode(passwordEncoder);

        return  userMapper.userEntityToUserResponseDto(userRepository.save(userEntity));
    }

    public void deleteUser(UserDto.UserDeleteDto deleteDto){
        UserEntity userEntity = userRepository.findById(deleteDto.getUserId())
                .orElseThrow(()-> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        if(!userEntity.getProfileImage().equals(DEFAULT_PROFILE)){
            s3FileService.deleteMultiFile(userEntity.getProfileImage());
        }
        userRepository.deleteById(deleteDto.getUserId());
        log.info("{}: User 삭제됨 !", deleteDto.getUserId());
    }

    @Transactional
    public UserDto.UserResponseDto modifyUser(UserDto.UserPatchDto patchDto){

        UserEntity userEntity = userRepository.findById(patchDto.getUserId()).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        S3ImageInfo s3ImageInfo;

        if (patchDto.getProfileImage() == null) { // 프로필 이미지 변경 X
            s3ImageInfo = S3ImageInfo.builder().fileURI(userEntity.getProfileImage()).build();
        } else { // 프로필 이미지도 변경
            try {
                //기존 이미지 제거
                String imgUrl = s3FileService.findImgUrl(userEntity.getProfileImage());
                s3FileService.deleteMultiFile(imgUrl);
            } catch (Exception e) {
                log.info("FILE_IS_NOT_EXIST_IN_BUCKET");
            }

            s3ImageInfo = s3FileService.uploadMultiFile(patchDto.getProfileImage());
        }

        if (patchDto.getDescription() == null) {
            patchDto.setDescription(userEntity.getDescription());
        }
        if (patchDto.getNickname() == null) {
            patchDto.setNickname(userEntity.getUserRealName());
        }
        if (patchDto.getPassword() == null) {
            patchDto.setNickname(userEntity.getPassword());
        }

        userEntity.update(patchDto, s3ImageInfo);
        return userMapper.userEntityToUserResponseDto(userEntity);
    }

    public UserDto.UserResponseDto getUser(Long userId){
        return userMapper.userEntityToUserResponseDto(
                userRepository.findById(userId).orElseThrow());
    }
    @Transactional
    public String logOutUser(UserDto.UserLogOutDto userLogOutDto) {
        UserEntity userEntity = userRepository.findByUserEmail(userLogOutDto.getUserEmail())
                .orElseThrow(()-> new BusinessLogicException(ExceptionCode.UNAUTHORIZED_MEMBER));
        userEntity.userLogOut();
        return "logOut complete " + userEntity.getUserEmail();

    }
}
