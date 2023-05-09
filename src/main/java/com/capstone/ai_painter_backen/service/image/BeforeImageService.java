package com.capstone.ai_painter_backen.service.image;

import com.capstone.ai_painter_backen.domain.image.BeforeImageEntity;
import com.capstone.ai_painter_backen.dto.image.S3ImageInfo;
import com.capstone.ai_painter_backen.dto.image.BeforeImageDto;
import com.capstone.ai_painter_backen.exception.BusinessLogicException;
import com.capstone.ai_painter_backen.exception.ExceptionCode;
import com.capstone.ai_painter_backen.mapper.image.BeforeImageMapper;
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

    public BeforeImageDto.BeforeImageResponseDto createBeforeImage(BeforeImageDto.BeforeImagePostDto beforeImagePostDto){

        MultipartFile multipartFile = beforeImagePostDto.getBeforeImageMultipartFile();
        S3ImageInfo s3ImageInfo = s3FileService.uploadMultiFile(multipartFile);
        log.info(s3ImageInfo.toString());

        BeforeImageEntity beforeImageEntity =
                beforeImageMapper.BeforeImagePostDtoToBeforeImageEntity(
                        beforeImagePostDto,
                        s3ImageInfo,
                        userRepository.findById(beforeImagePostDto.getUserId())//유저 정보 없으면 오류 던짐.
                                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND)));


        BeforeImageEntity savedBeforeImageEntity =
                beforeImageRepository.save(beforeImageEntity);


        return beforeImageMapper.BeforeImageEntityToBeforeImageResponseDto(savedBeforeImageEntity);

    }

    public BeforeImageDto.BeforeImageResponseDto readBeforeImage(Long beforeImageId){

        BeforeImageEntity beforeImageEntity = beforeImageRepository.findById(beforeImageId)
                .orElseThrow(()->new BusinessLogicException(ExceptionCode.FILE_IS_NOT_EXIST));
        return beforeImageMapper.BeforeImageEntityToBeforeImageResponseDto(beforeImageEntity);
    }

    @Transactional
    public String deleteBeforeImage(Long beforeImageId){

        BeforeImageEntity beforeImageEntity = beforeImageRepository.findById(beforeImageId)
                .orElseThrow();
        beforeImageEntity.unSetUserEntity();//FK 제거
        String deletedFileName = s3FileService.deleteMultiFile(beforeImageEntity.getBeforeImageUri());//s3 내부 이미지 삭제함.
        beforeImageRepository.deleteById(beforeImageId);


        return "delete complete : "+ String.valueOf(beforeImageId)+"   deletedFileName: "+deletedFileName;
    }




}
