package com.capstone.ai_painter_backen.mapper.mentor;

import com.capstone.ai_painter_backen.domain.mentor.PortfolioEntity;
import com.capstone.ai_painter_backen.domain.mentor.TutorEntity;
import com.capstone.ai_painter_backen.dto.image.S3ImageInfo;
import com.capstone.ai_painter_backen.dto.mentor.PortfolioDto;
import org.mapstruct.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;


@Mapper(componentModel = "spring")
public interface PortfolioMapper  {
    default PortfolioEntity s3ImageInfoToPortfolioEntity(S3ImageInfo s3ImageInfo){
        return PortfolioEntity.builder()
                .imageUri(s3ImageInfo.getFileURI())
                .build();
    }

    default PortfolioDto.PortfolioResponseDto portfolioEntityToResponseDto(PortfolioEntity portfolioEntity){
        return PortfolioDto.PortfolioResponseDto.builder()
                .portfolioId(portfolioEntity.getId())
                .tutorId(portfolioEntity.getTutorEntity().getId())
                .imageUri(portfolioEntity.getImageUri())
                .build();
    }
}
