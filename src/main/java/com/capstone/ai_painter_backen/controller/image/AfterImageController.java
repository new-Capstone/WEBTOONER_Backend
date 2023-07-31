package com.capstone.ai_painter_backen.controller.image;

import com.capstone.ai_painter_backen.dto.Result;
import com.capstone.ai_painter_backen.dto.image.AfterImageDto;
import com.capstone.ai_painter_backen.dto.image.BeforeImageDto;
import com.capstone.ai_painter_backen.dto.mentor.PortfolioDto;
import com.capstone.ai_painter_backen.service.image.AfterImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    @Operation(summary = "변환 후의 이미지를 아이디를 통해서 가져오는 메소드")
    @ApiResponses({@ApiResponse(responseCode = "201" ,description = "사진이 정상 등록됨",
            content = @Content(schema = @Schema(implementation = AfterImageDto.AfterImageResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")})
    public ResponseEntity getAfterImage(@RequestParam Long afterImageId){
        return ResponseEntity.ok().body(afterImageService.readAfterImage(afterImageId));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "변환후의 이미지 스냅샷 저장 메소드", description = "변환 후의 이미지 스냅샷을 저장하는 메소드입니다.")
    @ApiResponses({@ApiResponse(responseCode = "201" ,description = "사진이 정상 등록됨",
            content = {
                    @Content(array = @ArraySchema( schema  = @Schema (implementation = AfterImageDto.AfterImageResponseDto.class))),
            } ),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")})
    public ResponseEntity postAfterImageList(@RequestParam Long beforeImageId, @RequestPart List<MultipartFile> beforeImages){
        List<AfterImageDto.AfterImageResponseDto> afterImageResponseDtos = afterImageService.createAfterImageList(beforeImageId,beforeImages);
        return ResponseEntity.ok().body(afterImageResponseDtos);
    }

    @DeleteMapping
    @Operation(summary = "변환후의 이미지 스냅샷 삭제 메소드", description = "변환 후의 이미지 스냅샷을 삭제하는 메소드입니다.")
    @ApiResponses({@ApiResponse(responseCode = "201" ,description = "사진이 정상 등록됨",
            content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")})
    public ResponseEntity deleteAfterImages(@RequestParam Long afterImageId){
        return ResponseEntity.ok().body(afterImageService.deleteAfterImage(
                AfterImageDto.AfterImageDeleteDto.builder().afterImageId(afterImageId).build()));
    }

    @Operation(summary = "변환전 변환전 아이디를 통해서 이미지 가져오기", description = "변환전 이미지를 id를 통해서 변화 후 이미지를 읽어오는 메소드입니다.")
    @ApiResponses({@ApiResponse(responseCode = "201" ,description = "변환후 이미지를 변환전 사진 아이디를 기준으로 가져와짐",
            content = {
                    @Content(array = @ArraySchema( schema  = @Schema (implementation = AfterImageDto.AfterImageResponseDto.class))),
            }),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!", content = @Content),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.", content = @Content)})
    @GetMapping("/userId")
    public ResponseEntity<?> getByUserId(@RequestParam Long beforeImageId){
        try{
            List<AfterImageDto.AfterImageResponseDto> afterImageResponseDtos = afterImageService.
                    readAfterImageList(beforeImageId);
            return ResponseEntity.ok().body(afterImageResponseDtos);

        }catch (Exception e){

            String error = e.getMessage();
            Result<BeforeImageDto.BeforeImageResponseDto> response =
                    Result.<BeforeImageDto.BeforeImageResponseDto>builder().error(error).build();

            return ResponseEntity.badRequest().body(response);
        }
    }

}
