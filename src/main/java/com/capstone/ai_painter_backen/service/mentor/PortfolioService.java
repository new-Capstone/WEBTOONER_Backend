package com.capstone.ai_painter_backen.service.mentor;

import com.capstone.ai_painter_backen.domain.mentor.CategoryEntity;
import com.capstone.ai_painter_backen.domain.mentor.PortfolioEntity;
import com.capstone.ai_painter_backen.domain.mentor.TutorEntity;
import com.capstone.ai_painter_backen.dto.image.S3ImageInfo;
import com.capstone.ai_painter_backen.dto.mentor.PortfolioDto;
import com.capstone.ai_painter_backen.exception.BusinessLogicException;
import com.capstone.ai_painter_backen.exception.ExceptionCode;
import com.capstone.ai_painter_backen.mapper.mentor.PortfolioMapper;
import com.capstone.ai_painter_backen.repository.mentor.CategoryRepository;
import com.capstone.ai_painter_backen.repository.mentor.PortfolioRepository;
import com.capstone.ai_painter_backen.repository.mentor.TutorRepository;
import com.capstone.ai_painter_backen.service.awsS3.S3FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final PortfolioMapper portfolioMapper;
    private final S3FileService s3FileService;
    private final TutorRepository tutorRepository;

    private final CategoryRepository categoryRepository;

    @Transactional
    public List<PortfolioDto.PortfolioResponseDto> createPortfolio(PortfolioDto.PortfolioPostDto postDto){

        List<MultipartFile> portfolios = postDto.getMultipartFiles();
        List<S3ImageInfo> s3ImageInfos = s3FileService.uploadMultiFileList(portfolios);//s3 upload

        TutorEntity tutor = tutorRepository.findById(postDto.getTutorId())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        List<PortfolioEntity> portfolioEntities = s3ImageInfos.stream().map(
                portfolioMapper::s3ImageInfoToPortfolioEntity
        ).collect(Collectors.toList());

//        portfolioEntities.stream().map(portfolioEntity -> {portfolioEntity.setTutorEntity(tutor)}).collect(Collectors.toList());

        for(PortfolioEntity portfolioEntity: portfolioEntities){
            portfolioEntity.setTutorEntity(tutor);
        }

        List<PortfolioEntity> savedPortfolioEntity = portfolioRepository.saveAll(portfolioEntities);

        return savedPortfolioEntity.stream().map(portfolioMapper::portfolioEntityToResponseDto)
                .collect(Collectors.toList());
    }


    @Transactional
    public List<PortfolioDto.PortfolioResponseDto> createPortfolioWithCategory(PortfolioDto.PortfolioPostAndCategoryDto portfolioPostAndCategoryDto){
        CategoryEntity categoryEntity = categoryRepository.findCategoryEntityByCategoryName(portfolioPostAndCategoryDto.getCategory());
        if(categoryEntity == null){
            throw new IllegalArgumentException("there is no kind of category");
        }
        List<MultipartFile> portfolios = portfolioPostAndCategoryDto.getMultipartFiles();
        List<S3ImageInfo> s3ImageInfos = s3FileService.uploadMultiFileList(portfolios);//s3 upload

        TutorEntity tutor = tutorRepository.findById(portfolioPostAndCategoryDto.getTutorId())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        List<PortfolioEntity> portfolioEntities = s3ImageInfos.stream().map(
                portfolioMapper::s3ImageInfoToPortfolioEntity
        ).collect(Collectors.toList());

        for(PortfolioEntity portfolioEntity: portfolioEntities){
            portfolioEntity.setTutorEntity(tutor);
            portfolioEntity.setCategory(categoryEntity);
        }

        List<PortfolioEntity> savedPortfolioEntity = portfolioRepository.saveAll(portfolioEntities);

        return savedPortfolioEntity.stream().map(portfolioMapper::portfolioEntityToResponseDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PortfolioDto.PortfolioResponseDto readPortfolio(Long portfolioId){

        return portfolioMapper.portfolioEntityToResponseDto(
                portfolioRepository.findById(portfolioId).orElseThrow());
    }

    @Transactional(readOnly = true)
    public List<PortfolioDto.PortfolioResponseDto> readPortfolioByTutorId(Long tutorId){

        TutorEntity tutor = tutorRepository.findById(tutorId)
                .orElseThrow(()->new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        List<PortfolioEntity> portfolioEntities = portfolioRepository.findAllByTutorEntity(tutor);

        return portfolioEntities.stream()
                .map(portfolioMapper::portfolioEntityToResponseDto).collect(Collectors.toList());
    }

    private List<String> deleteS3file(List<PortfolioEntity> portfolioEntities){
        List<String> deleteString = portfolioEntities.stream().map(portfolioEntity -> {
            return s3FileService.deleteMultiFile(portfolioEntity.getImageUri());
        }).collect(Collectors.toList());

        return deleteString;
    }

    @Transactional
    public List<PortfolioDto.PortfolioResponseDto> updatePortfolio(PortfolioDto.PortfolioUpdateDto updateDto){

        List<MultipartFile> updateDtoMultipartFiles = updateDto.getMultipartFiles();
        List<S3ImageInfo> s3ImageInfos = s3FileService.uploadMultiFileList(updateDtoMultipartFiles);//update될 이미지 uri

        TutorEntity tutor = tutorRepository.findById(updateDto.getTutorId()).orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        List<PortfolioEntity> savedPortfolioEntity = tutor.getPortfolioEntities();
        deleteS3file(savedPortfolioEntity);//s3에서 삭제
        savedPortfolioEntity.clear();//지움

        List<PortfolioEntity> updatePortfolioEntities = s3ImageInfos.stream().map(portfolioMapper::s3ImageInfoToPortfolioEntity)
                .collect(Collectors.toList());//전환 s3ImageDto -> PortfolioEntity

        for(PortfolioEntity portfolioEntity: updatePortfolioEntities){
            portfolioEntity.setTutorEntity(tutor);
            savedPortfolioEntity.add(portfolioEntity);
        }

        List<PortfolioEntity> savedPortfolioEntity2 = portfolioRepository.saveAll(updatePortfolioEntities);//저장되어서 나옴...

        return savedPortfolioEntity2.stream().map(portfolioMapper::portfolioEntityToResponseDto)
                .collect(Collectors.toList());
    }
    @Transactional
    public PortfolioEntity deleteRelation(PortfolioEntity portfolioEntity){
        portfolioEntity.delete();
        return portfolioEntity;
    }

    @Transactional
    public String deletePortfolio(PortfolioDto.PortfolioDeleteDto deleteDto){//포트폴리오 전체 삭제...

        TutorEntity tutor = tutorRepository.findById(deleteDto.getTutorId())
                .orElseThrow(()->new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        s3FileService.deleteMultiFileList(tutor.getPortfolioEntities().stream()
                .map(PortfolioEntity::getImageUri).collect(Collectors.toList()));//사진 삭제 방법

        List<PortfolioEntity> portfolioEntities = tutor.getPortfolioEntities();//전체 포트 폴리오를 삭제함. casade type all 적용.
        portfolioEntities.stream().map(this::deleteRelation).collect(Collectors.toList());
        tutorRepository.save(tutor);


        return "portfolio 를 전체 삭제 합니다. deleted tutorId: "+ deleteDto.getTutorId();
    }

    @Transactional
    public String deletePortfolioOne(Long portfolioId){
        try{
            PortfolioEntity portfolioEntity = portfolioRepository.findById(portfolioId).orElseThrow();
            portfolioEntity.setTutorNull();
            s3FileService.deleteMultiFile(portfolioEntity.getImageUri());
            portfolioRepository.deleteById(portfolioId);
            return "deleted portfolio image uri: "+ portfolioId;

        }catch (IllegalArgumentException e){
            throw new RuntimeException("존재하지 않는 포트폴리오 아이디 입니다");
        }
    }


}
