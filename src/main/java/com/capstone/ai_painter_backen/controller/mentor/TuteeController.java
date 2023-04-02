package com.capstone.ai_painter_backen.controller.mentor;

import com.capstone.ai_painter_backen.dto.mentor.TuteeDto;
import com.capstone.ai_painter_backen.dto.mentor.TuteeDto.TuteeResponseDto;
import com.capstone.ai_painter_backen.service.mentor.TuteeService;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/new")
    public ResponseEntity<?> createTutee(@RequestBody @Schema(implementation = TuteeDto.TuteeRequestSaveDto.class)
                                                     TuteeDto.TuteeRequestSaveDto tuteeRequestSaveDto) {//todo 같은 유저를 두번 튜티로 받을 수 없게 해야함
        return ResponseEntity.ok().body(tuteeService.saveTutee(tuteeRequestSaveDto));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public TuteeResponseDto getTuteeInfo(@PathVariable Long id) {
        return tuteeService.findTuteeById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/edit")
    public TuteeResponseDto updateTutee(@Schema(implementation = TuteeRequestUpdateDto.class)
                                       @RequestBody TuteeRequestUpdateDto tuteeRequestUpdateDto) {
        return tuteeService.updateTutee(tuteeRequestUpdateDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}/delete")
    public void deleteTutee(@PathVariable Long id) {
        tuteeService.deleteTutee(TuteeRequestDeleteDto.builder().tuteeId(id).build());
    }
}
