package com.capstone.ai_painter_backen.controller.mentor;

import com.capstone.ai_painter_backen.dto.mentor.TuteeDto.RequestSaveDto;
import com.capstone.ai_painter_backen.dto.mentor.TuteeDto.RequestUpdateDto;
import com.capstone.ai_painter_backen.dto.mentor.TuteeDto.ResponseDto;
import com.capstone.ai_painter_backen.dto.mentor.TutorDto;
import com.capstone.ai_painter_backen.service.mentor.TuteeService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.capstone.ai_painter_backen.dto.mentor.TuteeDto.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tutees")
public class TuteeController {

    private final TuteeService tuteeService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/new")
    public void createTutee(@RequestBody @Schema(implementation = RequestSaveDto.class) RequestSaveDto requestSaveDto) {
        tuteeService.saveTutee(requestSaveDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public ResponseDto getTuteeInfo(@PathVariable Long id) {
        return tuteeService.findTuteeById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}/edit")
    public ResponseDto updateTutee(@PathVariable Long id, @RequestBody RequestUpdateDto requestUpdateDto) {
        return tuteeService.updateTutee(id, requestUpdateDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}/delete")
    public void deleteTutee(@PathVariable Long id) {
        tuteeService.deleteTutee(RequestDeleteDto.builder().tuteeId(id).build());
    }
}
