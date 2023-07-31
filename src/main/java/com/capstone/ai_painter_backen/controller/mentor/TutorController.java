package com.capstone.ai_painter_backen.controller.mentor;

import com.capstone.ai_painter_backen.dto.mentor.PortfolioDto;
import com.capstone.ai_painter_backen.dto.mentor.TutorDto;
import com.capstone.ai_painter_backen.service.mentor.TutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("tutorapi")
@AllArgsConstructor
public class TutorController {

    TutorService tutorService;

    @PostMapping()//test pass
    @Operation(summary = "튜터 정보생성 메소드", description = "튜터 기본 정보를 이용해서 튜터객체를 생성하는 메서드")
    @ApiResponses({@ApiResponse(responseCode = "201" ,description = "튜터의 정보가 정상적으로 생성됨",
            content = @Content(schema = @Schema(implementation = TutorDto.TutorResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!", content = @Content),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.", content = @Content)})
    public ResponseEntity<?> createTutor(@RequestBody @Schema(implementation = TutorDto.TutorPostDto.class)
                                             TutorDto.TutorPostDto tutorPostDto){
        return ResponseEntity.ok().body(tutorService.createTutor(tutorPostDto));
    }

    @GetMapping()//test pass
    @Operation(summary = "튜터 정보를 가져오는 메소드", description = "튜터의 아이디를 이용해서 튜터를 가져오는 메서드")
    @ApiResponses({@ApiResponse(responseCode = "201" ,description = "튜터의 정보가 정상적으로 가져와짐",
            content = @Content(schema = @Schema(implementation = TutorDto.TutorResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!", content = @Content),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.", content = @Content)})
    public ResponseEntity<?> getTutor(@RequestParam Long tutorId){
        return ResponseEntity.ok().body(tutorService.getTutor(tutorId));
    }

    @GetMapping("/all") //임시 경로
    @Operation(summary = "튜터 정보를 모두 가져오는 메소드", description = "모든 튜터 정보 가져오는 메서드")
    @ApiResponses({@ApiResponse(responseCode = "200" ,description = "튜터의 정보가 정상적으로 가져와짐",
            content = @Content(schema = @Schema(implementation = TutorDto.TutorResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!", content = @Content),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.", content = @Content)})
    public ResponseEntity<?> getTutors() {
        return ResponseEntity.ok().body(tutorService.getTutors());
    }

    @PatchMapping()//
    @Operation(summary = "튜터 정보를 수정하는 메소드", description = "튜터의 아이디를 이용해서 튜터의 정보를 수정하는 메서드")
    @ApiResponses({@ApiResponse(responseCode = "201" ,description = "튜터의 정보가 정상적으로 수정됨",
            content = @Content(schema = @Schema(implementation = TutorDto.TutorResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!", content = @Content),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.", content = @Content)})
    public ResponseEntity<?> modifyTutor(@RequestBody  @Schema(implementation = TutorDto.TutorPatchDto.class)
                                             TutorDto.TutorPatchDto tutorPatchDto){
        return ResponseEntity.ok().body(tutorService.modifyTutor(tutorPatchDto));
    }

    @DeleteMapping()//test pass
    @Operation(summary = "튜터 정보를 삭제하는 메소드", description = "튜터의 아이디를 이용해서 튜터를 삭제하는 메서드")
    @ApiResponses({@ApiResponse(responseCode = "201" ,description = "튜터의 정보가 정상적으로 가져와짐 삭제된 튜터의 아이디가 반환됨",
            content = @Content),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!", content = @Content),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.", content = @Content)})
    public ResponseEntity<?> deleteTutor(@RequestParam Long tutorId){
        tutorService.deleteTutor(new TutorDto.TutorDeleteDto(tutorId));
        return ResponseEntity.ok().body("deleted tutorId:"+tutorId);
    }

    //장르치면 이미지, 아이디

    @GetMapping("/allByCategory")
    @Operation(summary = "튜터 정보를 모두 가져오는 메소드 카테고리로", description = "모든 튜터 정보 가져오는 메서드")
    @ApiResponses({@ApiResponse(responseCode = "200" ,description = "튜터의 정보가 정상적으로 가져와짐",
            content = @Content(schema = @Schema(implementation = TutorDto.TutorResponseDtoIdAndImage.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!", content = @Content),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.", content = @Content)})
    public ResponseEntity<?> getTutorsByCategory(@RequestParam String categoryName) {
        return ResponseEntity.ok().body(tutorService.getTutorsByCategory(categoryName));
    }
}
