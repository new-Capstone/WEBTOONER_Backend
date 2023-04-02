package com.capstone.ai_painter_backen.controller.mentor;

import com.capstone.ai_painter_backen.dto.mentor.TutorDto;
import com.capstone.ai_painter_backen.service.mentor.TutorService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tutorapi")
@AllArgsConstructor
public class TutorController {

    TutorService tutorService;

    @PostMapping()//test pass
    public ResponseEntity<?> createTutor(@RequestBody @Schema(implementation = TutorDto.TutorPostDto.class) TutorDto.TutorPostDto tutorPostDto){
        return ResponseEntity.ok().body(tutorService.createTutor(tutorPostDto));
    }

    @GetMapping()//test pass
    public ResponseEntity<?> getTutor(@RequestParam Long tutorId){
        return ResponseEntity.ok().body(tutorService.getTutor(tutorId));
    }

    @PatchMapping()//
    public ResponseEntity<?> modifyTutor(@RequestBody  @Schema(implementation = TutorDto.TutorPatchDto.class) TutorDto.TutorPatchDto tutorPatchDto){
        return ResponseEntity.ok().body(tutorService.modifyTutor(tutorPatchDto));
    }

    @DeleteMapping()//test pass
    public ResponseEntity<?> deleteTutor(@RequestParam Long tutorId){
        tutorService.deleteTutor(new TutorDto.TutorDeleteDto(tutorId));
        return ResponseEntity.ok().body("deleted tutorId:"+tutorId);
    }

}
