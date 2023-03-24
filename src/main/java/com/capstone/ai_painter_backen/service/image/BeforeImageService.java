package com.capstone.ai_painter_backen.service.image;

import com.capstone.ai_painter_backen.domain.image.BeforeImageEntity;
import com.capstone.ai_painter_backen.dto.S3ImageInfo;
import com.capstone.ai_painter_backen.dto.image.BeforeImageDto;
import com.capstone.ai_painter_backen.mapper.BeforeImageMapper;
import com.capstone.ai_painter_backen.repository.UserRepository;
import com.capstone.ai_painter_backen.repository.image.BeforeImageRepository;
import com.capstone.ai_painter_backen.service.awsS3.S3FileService;
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
    S3FileService s3FileService;
    UserRepository userRepository;

    public BeforeImageDto.ResponseDto createBeforeImage(BeforeImageDto.PostDto postDto){

        MultipartFile multipartFile = postDto.getBeforeImageMultipartFile();
        S3ImageInfo s3ImageInfo = s3FileService.uploadMultiFile(multipartFile);
        log.info(s3ImageInfo.toString());

        BeforeImageEntity beforeImageEntity =
                beforeImageMapper.BeforeImagePostDtoToBeforeImageEntity(postDto,s3ImageInfo);

//        Long userId = postDto.getUserId();//todo 나중에 유저 추가되면 연간관계 수행할것.
//        beforeImageEntity.setUserEntity(userRepository.findById(userId).orElseThrow());

        BeforeImageEntity savedBeforeImageEntity =
                beforeImageRepository.save(beforeImageEntity);

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

        BeforeImageEntity beforeImageEntity = beforeImageRepository.findById(beforeImageId).orElseThrow();
        beforeImageRepository.deleteById(beforeImageId);
        String deletedFileName = s3FileService.deleteMultiFile(beforeImageEntity.getBeforeImageUri());//s3 내부 이미지 삭제함.

        return "delete complete : "+ String.valueOf(beforeImageId)+"   deletedFileName: "+deletedFileName;
    }




}
