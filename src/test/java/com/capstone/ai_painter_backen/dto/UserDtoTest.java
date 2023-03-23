package com.capstone.ai_painter_backen.dto;

import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.mapper.UserMapper;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserDtoTest {
    @Test
    @DisplayName("dtoToEntity")
    void dtoToEntity() {
        // given
        UserDto.PostDto userDto = UserDto.PostDto.builder()
                .username("1234")
                .password("1234")
                .loginId("1234")
                .description("1234")
                .profileImage("1234").build();
        UserEntity userEntity = UserMapper.INSTANCE.userRequestPostDtoToUserEntity(userDto);
        Assert.assertEquals("같아야한다.",userEntity.getUsername(),userDto.getUsername());
        System.out.println(userEntity.getUsername());


        // when

        // then
    }
}