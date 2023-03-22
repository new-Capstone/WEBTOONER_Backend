package com.capstone.ai_painter_backen.controller;

import com.capstone.ai_painter_backen.controller.dto.TuteeDto.RequestSaveDto;
import com.capstone.ai_painter_backen.controller.dto.TuteeDto.RequestUpdateDto;
import com.capstone.ai_painter_backen.controller.dto.TuteeDto.ResponseDto;
import com.capstone.ai_painter_backen.service.TuteeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.capstone.ai_painter_backen.controller.dto.TuteeDto.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tutees")
public class TuteeController {

    private final TuteeService tuteeService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/new")
    public void createTutee(@RequestBody RequestSaveDto requestSaveDto) {
        tuteeService.saveTutee(requestSaveDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public ResponseDto getTuteeInfo(@PathVariable Long id) {
        return tuteeService.findTuteeById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}/edit")
    public void updateTutee(@PathVariable Long id, @RequestBody RequestUpdateDto requestUpdateDto) {
        tuteeService.updateTutee(id, requestUpdateDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}/delete")
    public void deleteTutee(@PathVariable Long id) {
        tuteeService.deleteTutee(RequestDeleteDto.builder().tuteeId(id).build());
    }
}
