package com.capstone.ai_painter_backen.controller;

import com.capstone.ai_painter_backen.dto.image.BeforeImageDto;
import com.capstone.ai_painter_backen.service.image.BeforeImageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@RestController
@AllArgsConstructor
@RequestMapping("beforeimage")
public class BeforeImageController {

    BeforeImageService beforeImageService;
    @PostMapping()
    public ResponseEntity<?> createBeforeImage(@RequestParam MultipartFile multipartFile){
        BeforeImageDto.ResponseDto responseDto = beforeImageService.createBeforeImage(multipartFile);
        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteBeforeImage(@RequestParam Long beforeImageId){
        return ResponseEntity.ok().body(beforeImageService.deleteBeforeImage(beforeImageId));
    }





}
