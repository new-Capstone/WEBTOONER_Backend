package com.capstone.ai_painter_backen.mapper.message;

import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.domain.message.MessageEntity;
import com.capstone.ai_painter_backen.domain.message.RoomEntity;
import com.capstone.ai_painter_backen.dto.Message.MessageDto;
import com.capstone.ai_painter_backen.dto.Message.RoomDto;
import com.capstone.ai_painter_backen.dto.Message.RoomDto.RoomResponseDto;
import com.capstone.ai_painter_backen.dto.UserDto;
import com.capstone.ai_painter_backen.dto.UserDto.UserResponseDto;
import org.mapstruct.Mapper;

import java.text.SimpleDateFormat;

@Mapper(componentModel = "spring")
public interface MessageMapper {
}
