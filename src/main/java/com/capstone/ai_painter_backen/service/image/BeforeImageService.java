package com.capstone.ai_painter_backen.service.image;

import com.capstone.ai_painter_backen.domain.image.BeforeImageEntity;
import com.capstone.ai_painter_backen.dto.image.S3ImageInfo;
import com.capstone.ai_painter_backen.dto.image.BeforeImageDto;
import com.capstone.ai_painter_backen.exception.BusinessLogicException;
import com.capstone.ai_painter_backen.exception.ExceptionCode;
import com.capstone.ai_painter_backen.mapper.image.BeforeImageMapper;
import com.capstone.ai_painter_backen.repository.UserRepository;
import com.capstone.ai_painter_backen.repository.image.beforeimage.BeforeImageRepository;
import com.capstone.ai_painter_backen.service.awsS3.S3FileService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class BeforeImageService {

    BeforeImageRepository beforeImageRepository;
    BeforeImageMapper beforeImageMapper;
    S3FileService s3FileService;
    UserRepository userRepository;
    ClientUtils clientUtils;
    AfterImageService afterImageService;


    public BeforeImageDto.BeforeImageResponseDto createBeforeImage(BeforeImageDto.BeforeImagePostDto beforeImagePostDto){

        MultipartFile multipartFile = beforeImagePostDto.getBeforeImageMultipartFile();
        S3ImageInfo s3ImageInfo = s3FileService.uploadMultiFile(multipartFile);
        log.info(s3ImageInfo.toString());

        try {
            List<MultipartFile> transformedImage = clientUtils.requestImage(multipartFile, beforeImagePostDto.getExpression(),
                    beforeImagePostDto.getModel().toLowerCase(), beforeImagePostDto.getGender(), beforeImagePostDto.getModelName());
            BeforeImageEntity beforeImageEntity =
                    beforeImageMapper.BeforeImagePostDtoToBeforeImageEntity(
                            beforeImagePostDto,
                            s3ImageInfo,
                            userRepository.findById(beforeImagePostDto.getUserId())//유저 정보 없으면 오류 던짐.
                                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND)));


            BeforeImageEntity savedBeforeImageEntity =
                    beforeImageRepository.save(beforeImageEntity);

            //TODO : refactoring (AfterImageService, BeforeImageService 분리)
            afterImageService.createAfterImageList(savedBeforeImageEntity.getId(), transformedImage);

            //생성되는 이미지 dto에 추가해서 return
            return beforeImageMapper.BeforeImageEntityToBeforeImageResponseDto(savedBeforeImageEntity);

        }catch (Exception e) {
            log.info("Error while server communication");
            return null;
        }
    }
    @Transactional
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

    @Transactional(readOnly=true)
    public Page<BeforeImageDto.BeforeImageResponseDto> readBeforeImageEntityByUserId(Pageable pageable, Long userId){

        try{
            Page<BeforeImageEntity> beforeImageEntities = beforeImageRepository.findAllByUserEntityId(userId, pageable);
            List<BeforeImageDto.BeforeImageResponseDto> beforeImageResponseDtos = beforeImageEntities
                    .stream().map(beforeImageMapper::BeforeImageEntityToBeforeImageResponseDto)
                    .collect(Collectors.toList());

            return new PageImpl<>(beforeImageResponseDtos,
                    beforeImageEntities.getPageable(),
                    beforeImageEntities.getNumber());

        }catch (Exception e){
            throw new IllegalArgumentException("there is no user");
        }
    }
}
