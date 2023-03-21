package com.capstone.ai_painter_backen.controller;

import com.capstone.ai_painter_backen.dto.image.BeforeImageDto;
import com.capstone.ai_painter_backen.service.image.BeforeImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "이미지 스냅샷 저장 메소드", description = "이미지 스냅샷을 저장하는 메소드입니다.")
    @ApiResponses({@ApiResponse(responseCode = "201" ,description = "사진이 정상 등록됨",
            content = @Content(schema = @Schema(implementation = BeforeImageDto.ResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")})
    public ResponseEntity<?> createBeforeImage(@ModelAttribute @Parameter BeforeImageDto.PostDto postDto){
        BeforeImageDto.ResponseDto responseDto = beforeImageService.createBeforeImage(postDto.getBeforeImageMultipartFile());
        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteBeforeImage(@RequestParam Long beforeImageId){
        return ResponseEntity.ok().body(beforeImageService.deleteBeforeImage(beforeImageId));
    }





}
