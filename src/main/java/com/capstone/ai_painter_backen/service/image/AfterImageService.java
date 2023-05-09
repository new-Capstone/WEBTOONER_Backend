package com.capstone.ai_painter_backen.service.image;

import com.capstone.ai_painter_backen.domain.image.AfterImageEntity;
import com.capstone.ai_painter_backen.domain.image.BeforeImageEntity;
import com.capstone.ai_painter_backen.dto.image.S3ImageInfo;
import com.capstone.ai_painter_backen.dto.image.AfterImageDto;
import com.capstone.ai_painter_backen.exception.BusinessLogicException;
import com.capstone.ai_painter_backen.exception.ExceptionCode;
import com.capstone.ai_painter_backen.mapper.image.AfterImageMapper;
import com.capstone.ai_painter_backen.repository.image.AfterImageRepository;
import com.capstone.ai_painter_backen.repository.image.BeforeImageRepository;
import com.capstone.ai_painter_backen.service.awsS3.S3FileService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AfterImageService {


    private final AfterImageMapper afterImageMapper;
    private final AfterImageRepository afterImageRepository;

    private final BeforeImageRepository beforeImageRepository;
    private final S3FileService s3FileService;



    public AfterImageDto.AfterImageResponseDto readAfterImage(Long afterImageId){
        AfterImageEntity afterImageEntity = afterImageRepository.findById(afterImageId).orElseThrow();
        return afterImageMapper.afterImageEntityToAfterImageDto(afterImageEntity);
    }


    public List<AfterImageDto.AfterImageResponseDto> createAfterImageList(Long beforeImageId, List<MultipartFile> afterImages) {
        BeforeImageEntity beforeImageEntity = beforeImageRepository.findById(beforeImageId).orElseThrow();

        List<AfterImageEntity> savedAfterImageEntities = beforeImageEntity.getAfterImageEntities();
        List<S3ImageInfo> s3ImageInfos = s3FileService.uploadMultiFileList(afterImages);

        for (S3ImageInfo s3ImageInfo: s3ImageInfos){
            savedAfterImageEntities.add(AfterImageEntity.builder()
                    .beforeImageEntity(beforeImageEntity)
                    .imageURI(s3ImageInfo.getFileURI())
                    .build());
        }

        BeforeImageEntity savedBeforeImageEntity = beforeImageRepository.save(beforeImageEntity);//jpa context todo CasCade persist
        return savedBeforeImageEntity.getAfterImageEntities().stream()
                .map(afterImageMapper::afterImageEntityToAfterImageDto).collect(Collectors.toList());
    }

    public String deleteAfterImage(AfterImageDto.AfterImageDeleteDto afterImageDeleteDto){
        AfterImageEntity afterImageEntity = afterImageRepository.findById(afterImageDeleteDto.getAfterImageId())
                .orElseThrow(()->new BusinessLogicException(ExceptionCode.FILE_IS_NOT_EXIST));

        afterImageEntity.unSetBeforeImageEntity();

        String deletedFileName = s3FileService.deleteMultiFile(afterImageEntity.getImageURI());

        afterImageRepository.deleteById(afterImageDeleteDto.getAfterImageId());
        return "fileName: " +deletedFileName+" afterImageId: "+afterImageDeleteDto.getAfterImageId();
    }
}
