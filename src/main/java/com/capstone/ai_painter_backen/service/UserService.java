package com.capstone.ai_painter_backen.service;

import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.dto.UserDto;
import com.capstone.ai_painter_backen.dto.image.S3ImageInfo;
import com.capstone.ai_painter_backen.exception.DuplicateNameException;
import com.capstone.ai_painter_backen.mapper.UserMapper;
import com.capstone.ai_painter_backen.repository.UserRepository;
import com.capstone.ai_painter_backen.service.awsS3.S3FileService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final S3FileService s3FileService;

    //profile image 추가
    @Transactional
    public UserDto.UserResponseDto createUser(UserDto.UserPostDto userPostDto){
        S3ImageInfo s3ImageInfo = s3FileService.uploadMultiFile(userPostDto.getProfileImage());
        UserEntity userEntity = userMapper.userRequestPostDtoToUserEntity(userPostDto, s3ImageInfo);

        if (userRepository.existsByLoginId(userEntity.getLoginId())) {
            throw new DuplicateNameException();
        }

        return  userMapper.userEntityToUserResponseDto(userRepository.save(userEntity));
    }

    //회원 탈퇴? 없을듯? (논의 필요)
    public void deleteUser(UserDto.UserDeleteDto deleteDto){
        userRepository.deleteById(deleteDto.getUserid());
        log.info("{}: User 삭제됨 !", deleteDto.getUserid());
    }

    //profile image 변경 기능 추가
    @Transactional
    public UserDto.UserResponseDto modifyUser(UserDto.UserPatchDto userPatchDto){
        UserEntity userEntity = userRepository.findById(userPatchDto.getUserid()).orElseThrow();
//        if(!sample.isPresent()) {    ---->orElseThrow()와 동일
//            throw new IllegalArgumentException();
//        }

        //S3에 있는 기존 image 삭제 후 새로운 image 저장
        String imgUrl = s3FileService.findImgUrl(userEntity.getProfileUri());
        s3FileService.deleteMultiFile(imgUrl);
        S3ImageInfo s3ImageInfo = s3FileService.uploadMultiFile(userPatchDto.getProfileImage());

        userEntity.update(userPatchDto, s3ImageInfo);
        return userMapper.userEntityToUserResponseDto(userEntity);
    }

    public UserDto.UserResponseDto getUser(Long userId){
        return userMapper.userEntityToUserResponseDto(
                userRepository.findById(userId).orElseThrow());
    }
}
