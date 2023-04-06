package com.capstone.ai_painter_backen.controller.mentor;

import com.capstone.ai_painter_backen.dto.mentor.PortfolioDto;
import com.capstone.ai_painter_backen.service.mentor.PortfolioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;
    @GetMapping
    public ResponseEntity<?> readPortfolio(@RequestParam Long portfolioId){
        return ResponseEntity.ok().body(portfolioService.readPortfolio(portfolioId));
    }

    @GetMapping("tutorid")
    public ResponseEntity<?> readPortfolioByTutorId(@RequestParam Long tutorId){//굳이 만들 필요가 없어보임.
        return ResponseEntity.ok().body(portfolioService.readPortfolioByTutorId(tutorId));
    }

    @PostMapping()
    public ResponseEntity creatPortfolio(@RequestParam Long tutorId, @RequestPart List<MultipartFile> multipartFileList){

        PortfolioDto.PortfolioPostDto postDto = PortfolioDto.PortfolioPostDto.builder()
                .multipartFiles(multipartFileList)
                .tutorId(tutorId)
                .build();

       return ResponseEntity.ok().body(portfolioService.createPortfolio(postDto));
    }

    @PatchMapping
    public ResponseEntity updatePortfolio(@RequestParam Long tutorId, @RequestPart List<MultipartFile> multipartFiles){
        PortfolioDto.PortfolioUpdateDto portfolioUpdateDto = PortfolioDto.PortfolioUpdateDto.builder()
                .multipartFiles(multipartFiles)
                .tutorId(tutorId)
                .build();
        return ResponseEntity.ok().body(portfolioService.updatePortfolio(portfolioUpdateDto));
    }


}
