package com.capstone.ai_painter_backen.dto.mentor;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class PortfolioDto {
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Schema
    public static class PortfolioResponseDto{
        Long portfolioId;
        String imageUri;
        Long tutorId;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Schema
    public static class PortfolioPostDto{// todo controller 에서 multipart file 받을 것
        Long tutorId;
        List<MultipartFile> multipartFiles = new ArrayList<>();
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Schema
    public static class PortfolioUpdateDto{ // todo controller 에서 multipart file 받을 것
        Long tutorId;
        List<MultipartFile> multipartFiles = new ArrayList<>();
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Schema
    public static class PortfolioDeleteDto{
        Long tutorId;
    }
}
