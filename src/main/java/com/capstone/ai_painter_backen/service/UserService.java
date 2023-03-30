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
    public UserDto.ResponseDto createUser(UserDto.PostDto postDto){
        UserEntity userEntity = userMapper.userRequestPostDtoToUserEntity(postDto);

        if (userRepository.existsByLoginId(userEntity.getLoginId())) {
            throw new DuplicateNameException();
        }

        return  userMapper.userEntityToUserResponseDto(userRepository.save(userEntity));
    }

    public void deleteUser(UserDto.DeleteDto deleteDto){
        userRepository.deleteById(deleteDto.getUserid());
        log.info("{}: User 삭제됨 !", deleteDto.getUserid());
    }

    @Transactional
    public UserDto.ResponseDto modifyUser(UserDto.PatchDto patchDto){

        UserEntity userEntity = userRepository.findById(patchDto.getUserid()).orElseThrow();
//        if(!sample.isPresent()) {    ---->orElseThrow()와 동일
//            throw new IllegalArgumentException();
//        }
        userEntity.update(patchDto);
        return userMapper.userEntityToUserResponseDto(userEntity);
    }

    public UserDto.ResponseDto getUser(Long userId){
        return userMapper.userEntityToUserResponseDto(
                userRepository.findById(userId).orElseThrow());
    }
}
