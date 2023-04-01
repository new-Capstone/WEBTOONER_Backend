package com.capstone.ai_painter_backen.controller.mentor;

import com.capstone.ai_painter_backen.dto.mentor.TutorDto;
import com.capstone.ai_painter_backen.service.mentor.TutorService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
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
    public ResponseEntity<?> createTutor(@RequestBody @Schema(implementation = TutorDto.PostDto.class) TutorDto.PostDto postDto){
        return ResponseEntity.ok().body(tutorService.createTutor(postDto));
    }

    @GetMapping()
    public ResponseEntity<?> getTutor(@RequestParam Long tutorId){
        return ResponseEntity.ok().body(tutorService.getTutor(tutorId));
    }

    @PatchMapping()
    public ResponseEntity<?> modifyTutor(@RequestBody  @Schema(implementation = TutorDto.PatchDto.class) TutorDto.PatchDto patchDto){
        return ResponseEntity.ok().body(tutorService.modifyTutor(patchDto));
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteTutor(@RequestParam Long tutorId){
        tutorService.deleteTutor(new TutorDto.DeleteDto(tutorId));
        return ResponseEntity.ok().body("deleted tutorId:"+tutorId);
    }

}
