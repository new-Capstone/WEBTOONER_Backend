package com.capstone.ai_painter_backen.controller.image;

import com.capstone.ai_painter_backen.dto.image.AfterImageDto;
import com.capstone.ai_painter_backen.service.image.AfterImageService;
import jakarta.servlet.annotation.MultipartConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("afterimage")
public class AfterImageController {

    AfterImageService afterImageService;

    @GetMapping
    public ResponseEntity getAfterImage(@RequestParam Long afterImageId){
        return ResponseEntity.ok().body(afterImageService.readAfterImage(afterImageId));
    }

    @PostMapping
    public ResponseEntity postAfterImageList(@RequestParam Long beforeImageId, @RequestPart List<MultipartFile> beforeImages){
        List<AfterImageDto.ResponseDto> responseDtos = afterImageService.createAfterImageList(beforeImageId,beforeImages);
        return ResponseEntity.ok().body(responseDtos);
    }

}
