package com.capstone.ai_painter_backen.controller.image;

import com.capstone.ai_painter_backen.dto.image.AfterImageDto;
import com.capstone.ai_painter_backen.service.image.AfterImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("afterimage")
public class AfterImageController {

    private final AfterImageService afterImageService;

    @GetMapping
    public ResponseEntity getAfterImage(@RequestParam Long afterImageId){
        return ResponseEntity.ok().body(afterImageService.readAfterImage(afterImageId));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity postAfterImageList(@RequestParam Long beforeImageId, @RequestPart List<MultipartFile> beforeImages){
        List<AfterImageDto.AfterImageResponseDto> afterImageResponseDtos = afterImageService.createAfterImageList(beforeImageId,beforeImages);
        return ResponseEntity.ok().body(afterImageResponseDtos);
    }

    @DeleteMapping
    public ResponseEntity deleteAfterImages(@RequestParam Long afterImageId){
        return ResponseEntity.ok().body(afterImageService.deleteAfterImage(
                AfterImageDto.AfterImageDeleteDto.builder().afterImageId(afterImageId).build()));
    }
}
