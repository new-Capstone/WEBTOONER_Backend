package com.capstone.ai_painter_backen.service.image;

import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.domain.image.BeforeImageEntity;
import com.capstone.ai_painter_backen.dto.Result;
import com.capstone.ai_painter_backen.dto.UserDto;
import com.capstone.ai_painter_backen.dto.image.S3ImageInfo;
import com.capstone.ai_painter_backen.dto.image.BeforeImageDto;
import com.capstone.ai_painter_backen.exception.BusinessLogicException;
import com.capstone.ai_painter_backen.exception.ExceptionCode;
import com.capstone.ai_painter_backen.mapper.image.BeforeImageMapper;
import com.capstone.ai_painter_backen.repository.UserRepository;
import com.capstone.ai_painter_backen.repository.image.BeforeImageRepository;
import com.capstone.ai_painter_backen.service.awsS3.S3FileService;
import com.capstone.ai_painter_backen.service.security.SecurityUserInfoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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

    private BeforeImageRepository beforeImageRepository;
    private BeforeImageMapper beforeImageMapper;
    private S3FileService s3FileService;
    private UserRepository userRepository;
    private ClientUtils clientUtils;
    private AfterImageService afterImageService;
    private SecurityUserInfoService securityUserInfoService;


    public BeforeImageDto.BeforeImageResponseDto createBeforeImage(BeforeImageDto.BeforeImagePostDto beforeImagePostDto){
        UserDto.CusTomUserPrincipalDto cusTomUserPrincipalDto = securityUserInfoService.getUserInfoFromSecurityContextHolder();

        UserEntity userEntity = userRepository.findByUserEmail(cusTomUserPrincipalDto.getEmail()).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

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
                            userEntity);


            BeforeImageEntity savedBeforeImageEntity =
                    beforeImageRepository.save(beforeImageEntity);

            afterImageService.createAfterImageList(savedBeforeImageEntity.getId(), transformedImage);

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
    public Page<BeforeImageDto.BeforeImageResponseDto> readBeforeImageEntityByUserId(Pageable pageable){

        try{
            UserDto.CusTomUserPrincipalDto cusTomUserPrincipalDto = securityUserInfoService.getUserInfoFromSecurityContextHolder();
            UserEntity userEntity = userRepository.findByUserEmail(cusTomUserPrincipalDto.getEmail()).orElseThrow(
                    () -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

            Page<BeforeImageEntity> beforeImageEntities = beforeImageRepository.findAllByUserEntityId(userEntity.getId(), pageable);
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
