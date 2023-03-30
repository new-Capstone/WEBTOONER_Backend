package com.capstone.ai_painter_backen.mapper.message;

import com.capstone.ai_painter_backen.domain.message.RoomEntity;
import com.capstone.ai_painter_backen.dto.Message.RoomDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    //    @Mapping(target = "id", ignore = true)
    default RoomEntity roomRequestPostDtoToRoomEntity(RoomDto.PostDto roompostDto) {
        if (roompostDto == null) {
            return null;
        } else {
            return RoomEntity.builder()
                    .messageEntities(new ArrayList<>())
                    .build();
        }
    }

    default RoomDto.ResponseDto roomEntityToRoomResponseDto(RoomEntity roomEntity){
        if (roomEntity == null) {
            return null;
        } else {
            return RoomDto.ResponseDto.builder()
                    .roomid(roomEntity.getId())
                    .owner(roomEntity.getOwner())
                    .visitor(roomEntity.getVisitor())
                    .messageEntities(roomEntity.getMessageEntities())
                    .build();
        }
    }

}

