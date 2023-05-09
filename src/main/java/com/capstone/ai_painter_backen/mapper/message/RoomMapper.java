package com.capstone.ai_painter_backen.mapper.message;

import com.capstone.ai_painter_backen.domain.message.RoomEntity;
import com.capstone.ai_painter_backen.dto.Message.RoomDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    //    @Mapping(target = "id", ignore = true)
    default RoomEntity roomRequestPostDtoToRoomEntity(RoomDto.RoomPostDto roomPostDto) {
        return RoomEntity.builder()
                .messageEntities(new ArrayList<>())
                .build();
    }

    default RoomDto.RoomResponseDto roomEntityToRoomResponseDto(RoomEntity roomEntity){
        if (roomEntity == null) {
            return null;
        } else {
            return RoomDto.RoomResponseDto.builder()
                    .roomId(roomEntity.getId())
                    .ownerId(roomEntity.getOwner().getId())
                    .visitorId(roomEntity.getVisitor().getId())
                    .build();
        }
    }

}

