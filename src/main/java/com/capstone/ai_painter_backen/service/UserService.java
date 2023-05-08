package com.capstone.ai_painter_backen.service;

import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.dto.UserDto;
import com.capstone.ai_painter_backen.exception.DuplicateNameException;
import com.capstone.ai_painter_backen.mapper.UserMapper;
import com.capstone.ai_painter_backen.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@AllArgsConstructor
public class UserService {
    UserMapper userMapper;
    UserRepository userRepository;

    @Transactional
    public UserDto.UserResponseDto createUser(UserDto.UserPostDto userPostDto){
        UserEntity userEntity = userMapper.userRequestPostDtoToUserEntity(userPostDto);

        if (userRepository.existsByUserEmail(userEntity.getUserEmail())) {
            throw new DuplicateNameException();
        }

        return  userMapper.userEntityToUserResponseDto(userRepository.save(userEntity));
    }

    public void deleteUser(UserDto.UserDeleteDto deleteDto){
        userRepository.deleteById(deleteDto.getUserId());
        log.info("{}: User 삭제됨 !", deleteDto.getUserId());
    }

    @Transactional
    public UserDto.UserResponseDto modifyUser(UserDto.UserPatchDto patchDto){

        UserEntity userEntity = userRepository.findById(patchDto.getUserId()).orElseThrow();
//        if(!sample.isPresent()) {    ---->orElseThrow()와 동일
//            throw new IllegalArgumentException();
//        }
        userEntity.update(patchDto);
        return userMapper.userEntityToUserResponseDto(userEntity);
    }

    public UserDto.UserResponseDto getUser(Long userId){
        return userMapper.userEntityToUserResponseDto(
                userRepository.findById(userId).orElseThrow());
    }
}
