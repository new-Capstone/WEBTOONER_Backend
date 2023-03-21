package com.capstone.ai_painter_backen.service.image;

import com.capstone.ai_painter_backen.domain.image.BeforeImageEntity;
import com.capstone.ai_painter_backen.dto.image.BeforeImageDto;
import com.capstone.ai_painter_backen.mapper.BeforeImageMapper;
import com.capstone.ai_painter_backen.repository.image.BeforeImageRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
@Slf4j
public class BeforeImageService {

    BeforeImageRepository beforeImageRepository;
    BeforeImageMapper beforeImageMapper;

    public BeforeImageDto.ResponseDto createBeforeImage(MultipartFile multipartFile){

        BeforeImageEntity savedBeforeImageEntity =
                beforeImageRepository.save(beforeImageMapper.MultipartFileToBeforeImageEntity(multipartFile));

        BeforeImageDto.ResponseDto responseDto =
                beforeImageMapper.BeforeImageEntityToBeforeImageResponseDto(savedBeforeImageEntity);

        return responseDto;
    }

    public BeforeImageDto.ResponseDto readBeforeImage(Long beforeImageId){
        BeforeImageEntity beforeImageEntity = beforeImageRepository.findById(beforeImageId).orElseThrow();
        return beforeImageMapper.BeforeImageEntityToBeforeImageResponseDto(beforeImageEntity);

    }

    @Transactional
    public String deleteBeforeImage(Long beforeImageId){
        beforeImageRepository.deleteById(beforeImageId);
        return "delete complete : "+ String.valueOf(beforeImageId);
    }




}
