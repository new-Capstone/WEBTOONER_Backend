package com.capstone.ai_painter_backen.controller.image;

import com.capstone.ai_painter_backen.domain.image.ImageContribute;
import com.capstone.ai_painter_backen.dto.Result;
import com.capstone.ai_painter_backen.dto.image.BeforeImageDto;
import com.capstone.ai_painter_backen.dto.mentor.TutorDto;
import com.capstone.ai_painter_backen.service.image.BeforeImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("beforeimage")
public class BeforeImageController {
//튜터 이미지..., 진수님 메세지, 재성...유저
    BeforeImageService beforeImageService;


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "이미지 스냅샷 저장 메소드", description = "이미지 스냅샷을 저장하는 메소드입니다.")
    @ApiResponses({@ApiResponse(responseCode = "201" ,description = "사진이 정상 등록됨",
            content = @Content(schema = @Schema(implementation = BeforeImageDto.BeforeImageResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!", content = @Content),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.", content = @Content)})
    public ResponseEntity<?> createBeforeImage(@RequestParam Long userId, @RequestParam String expression,
                                               @RequestParam String model, @RequestParam String gender,
                                               @RequestParam String loraName, @RequestPart MultipartFile multipartFile){
        BeforeImageDto.BeforeImagePostDto beforeImagePostDto =
                BeforeImageDto.BeforeImagePostDto.builder()
                        .beforeImageMultipartFile(multipartFile)
                        .expression(expression)
                        .gender(gender)
                        .modelName(loraName)
                        .model(model)
                        .userId(userId)
                        .build();

        BeforeImageDto.BeforeImageResponseDto beforeImageResponseDto = beforeImageService.createBeforeImage(beforeImagePostDto);
        return ResponseEntity.ok().body(beforeImageResponseDto);
    }

    @Operation(summary = "변환전 이미지 가져오기", description = "변환전 이미지를 id를 통해서 읽어오는 메소드입니다.")
    @ApiResponses({@ApiResponse(responseCode = "201" ,description = "변환전 이미지가 정상적으로 가져와짐",
            content = @Content(schema = @Schema(implementation = BeforeImageDto.BeforeImageResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!", content = @Content),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.", content = @Content)})
    @GetMapping
    public ResponseEntity<?> getBeforeImage(@RequestParam Long beforeImageId){
        return ResponseEntity.ok().body(beforeImageService.readBeforeImage(beforeImageId));
    }

    @Operation(summary = "변환전 유저 아이디를 통해서 이미지 가져오기", description = "변환전 이미지를 id를 통해서 읽어오는 메소드입니다.")
    @ApiResponses({@ApiResponse(responseCode = "201" ,description = "변환전 이미지를 유저 아이디를 기준으로 가져와짐",
            content = {
                    @Content(array = @ArraySchema( schema  = @Schema (implementation = BeforeImageDto.BeforeImageResponseDto.class))),
            }),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!", content = @Content),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.", content = @Content)})
    @GetMapping("/userId")
    public ResponseEntity<?> getByUserId(@RequestParam Long userId,
                                         @RequestParam(value = "pageNo", defaultValue = "0", required = false) Integer pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize){
        try{
            Pageable pageable = PageRequest.of( pageNum ,pageSize, Sort.by("id"));

            Page<BeforeImageDto.BeforeImageResponseDto> responseDtos = beforeImageService
                    .readBeforeImageEntityByUserId(pageable, userId);
            Result<List<BeforeImageDto.BeforeImageResponseDto>> responseDtoResult = Result.createPage(responseDtos);
            return ResponseEntity.ok().body(responseDtoResult);

        }catch (Exception e){

            String error = e.getMessage();
            Result<BeforeImageDto.BeforeImageResponseDto> response =
                    Result.<BeforeImageDto.BeforeImageResponseDto>builder().error(error).build();

            return ResponseEntity.badRequest().body(response);
        }
    }


    @Operation(summary = "변환전 이미지 삭제하기", description = "변환전 이미지를 id를 통해서 삭제하고 해당하는 변환 후 이미지도 삭제함 메소드입니다.")
    @ApiResponses({@ApiResponse(responseCode = "201" ,description = "문자열과 함께 삭제된 이미지 파일의 이름이 반환됨", content = @Content (schema = @Schema )),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!", content = @Content),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.", content = @Content)})
    @DeleteMapping()
    public ResponseEntity<?> deleteBeforeImage(@RequestParam Long beforeImageId){

        return ResponseEntity.ok().body(beforeImageService.deleteBeforeImage(beforeImageId));
    }





}
