package com.capstone.ai_painter_backen.controller.mentor;

import com.capstone.ai_painter_backen.dto.image.BeforeImageDto;
import com.capstone.ai_painter_backen.dto.mentor.PortfolioDto;
import com.capstone.ai_painter_backen.service.mentor.PortfolioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Array;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;
    @Operation(summary = "튜터 포트폴리오 이미지 가져오기", description = "포트폴리오 아이디 하나를 통해서 포트폴리오 이미지 하나를 읽어오는 메소드입니다.")
    @ApiResponses({@ApiResponse(responseCode = "201" ,description = "포트폴리오 이미지가 정상적으로 가져와짐",
            content = @Content(schema = @Schema(implementation = PortfolioDto.PortfolioResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!", content = @Content),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.", content = @Content)})
    @GetMapping
    public ResponseEntity<?> readPortfolio(@RequestParam Long portfolioId){
        return ResponseEntity.ok().body(portfolioService.readPortfolio(portfolioId));
    }


    @GetMapping("/tutorId")
    @Operation(summary = "이미지 스냅샷 저장 메소드", description = "이미지 스냅샷을 저장하는 메소드입니다.")
    @ApiResponses({@ApiResponse(responseCode = "201" ,description = "사진이 정상 등록됨",
            content = {
                    @Content(array = @ArraySchema ( schema  = @Schema (implementation = PortfolioDto.PortfolioResponseDto.class))),
            } ),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!", content = @Content),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.", content = @Content)})
    public ResponseEntity<?> readPortfolioByTutorId(@RequestParam Long tutorId){//굳이 만들 필요가 없어보임.
        return ResponseEntity.ok().body(portfolioService.readPortfolioByTutorId(tutorId));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "튜터 포트폴리오 저장 메소드", description = "튜터 아이디와 이미지 배열을 이용해서 한번에 여러장의 포트폴리오를 생성하는 메소드입니다.")
    @ApiResponses({@ApiResponse(responseCode = "201" ,description = "사진이 정상 등록됨",
            content = {
                    @Content(array = @ArraySchema ( schema  = @Schema (implementation = PortfolioDto.PortfolioResponseDto.class))),
            } ),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!", content = @Content),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.", content = @Content)})
    public ResponseEntity creatPortfolio(@RequestParam Long tutorId, @RequestPart List<MultipartFile> multipartFileList){

        PortfolioDto.PortfolioPostDto postDto = PortfolioDto.PortfolioPostDto.builder()
                .multipartFiles(multipartFileList)
                .tutorId(tutorId)
                .build();

       return ResponseEntity.ok().body(portfolioService.createPortfolio(postDto));
    }


    @PostMapping(value={"/category"}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "튜터 포트폴리오 저장 메소드", description = "튜터 아이디와 이미지 배열을 이용해서 한번에 여러장의 포트폴리오를 생성하는 메소드입니다.")
    @ApiResponses({@ApiResponse(responseCode = "201" ,description = "사진이 정상 등록됨",
            content = {
                    @Content(array = @ArraySchema ( schema  = @Schema (implementation = PortfolioDto.PortfolioResponseDto.class))),
            } ),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!", content = @Content),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.", content = @Content)})
    public ResponseEntity creatPortfolioWithCategory(@RequestParam Long tutorId,
                                                     @RequestParam String categoryName,
                                                     @RequestPart List<MultipartFile> multipartFileList){

        PortfolioDto.PortfolioPostAndCategoryDto postDto = PortfolioDto.PortfolioPostAndCategoryDto.builder()
                .category(categoryName)
                .multipartFiles(multipartFileList)
                .tutorId(tutorId)
                .build();

        return ResponseEntity.ok().body(portfolioService.createPortfolioWithCategory(postDto));
    }

    @PatchMapping (consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "튜터 포트폴리오 수정 메소드", description = "튜터 아이디와 이미지 배열을 이용해서 한번에 여러장의 포트폴리오를 수정하는! 메소드입니다.")
    @ApiResponses({@ApiResponse(responseCode = "201" ,description = "사진이 정상 등록됨",
            content = {
                    @Content(array = @ArraySchema ( schema  = @Schema (implementation = PortfolioDto.PortfolioResponseDto.class))),
            } ),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!", content = @Content),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.", content = @Content)})
    public ResponseEntity updatePortfolio(@RequestParam Long tutorId, @RequestPart List<MultipartFile> multipartFiles){
        PortfolioDto.PortfolioUpdateDto portfolioUpdateDto = PortfolioDto.PortfolioUpdateDto.builder()
                .multipartFiles(multipartFiles)
                .tutorId(tutorId)
                .build();
        return ResponseEntity.ok().body(portfolioService.updatePortfolio(portfolioUpdateDto));
    }


    @Operation(summary = "튜터 포트폴리오 이미지 삭제", description = "포트폴리오 아이디 하나를 통해서 포트폴리오 이미지 하나를 삭제하는! 메소드입니다.")
    @ApiResponses({@ApiResponse(responseCode = "201" ,description = "포트폴리오 이미지가 정상적으로 삭제된 파일의 문자열이 전송됨",
            content = @Content),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!", content = @Content),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.", content = @Content)})
    @DeleteMapping()
    public ResponseEntity deletePortfolio(@RequestParam Long portfolioId){
        return ResponseEntity.ok().body(portfolioService.deletePortfolioOne(portfolioId));
    }
}
