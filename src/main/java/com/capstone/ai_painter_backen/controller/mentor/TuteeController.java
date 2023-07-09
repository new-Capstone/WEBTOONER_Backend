package com.capstone.ai_painter_backen.controller.mentor;

import com.capstone.ai_painter_backen.dto.Message.NotificationDto;
import com.capstone.ai_painter_backen.dto.mentor.TuteeDto;
import com.capstone.ai_painter_backen.dto.mentor.TuteeDto.TuteeResponseDto;
import com.capstone.ai_painter_backen.service.mentor.TuteeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.capstone.ai_painter_backen.dto.mentor.TuteeDto.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tutees")
public class TuteeController {//swagger 때문에 dto class 이름 바꿀것
    // all api test pass!!!!!
    private final TuteeService tuteeService;

    @Operation(summary = "Tutee 생성 api", description = "userId, tutorId로 Tutee 생성")
    @ApiResponse(responseCode = "201", description = "Create Tutee",
            content = @Content(schema = @Schema(implementation = TuteeDto.TuteeResponseDto.class)))
    @ApiResponse(responseCode = "400", description = "Client Error")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/new")
    public ResponseEntity<?> createTutee(@Valid @RequestBody @Schema(implementation = TuteeDto.TuteeRequestSaveDto.class)
                                                     TuteeDto.TuteeRequestSaveDto tuteeRequestSaveDto) {
        return ResponseEntity.ok().body(tuteeService.saveTutee(tuteeRequestSaveDto));
    }

    @Operation(summary = "Tutee 조회 api", description = "tuteeId로 조회")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(schema = @Schema(implementation = TuteeDto.TuteeResponseDto.class)))
    @ApiResponse(responseCode = "400", description = "Client Error")
    @ApiResponse(responseCode = "404", description = "Not Found")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public TuteeResponseDto getTuteeInfo(@PathVariable Long id) {
        return tuteeService.findTuteeById(id);
    }

    @Operation(summary = "Tutee 수정 api", description = "tuteeId, tutorId 받아서 tutee의 tutor 변경")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(schema = @Schema(implementation = TuteeDto.TuteeResponseDto.class)))
    @ApiResponse(responseCode = "400", description = "Client Error")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/edit")
    public TuteeResponseDto updateTutee(@Valid @Schema(implementation = TuteeRequestUpdateDto.class)
                                       @RequestBody TuteeRequestUpdateDto tuteeRequestUpdateDto) {
        return tuteeService.updateTutee(tuteeRequestUpdateDto);
    }


    @Operation(summary = "Tutee 삭제 api", description = "tuteeId 받아서 삭제")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "400", description = "Client Error")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}/delete")
    public void deleteTutee(@PathVariable Long id) {
        tuteeService.deleteTutee(TuteeRequestDeleteDto.builder().tuteeId(id).build());
    }
}
