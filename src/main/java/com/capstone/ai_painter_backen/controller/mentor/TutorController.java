package com.capstone.ai_painter_backen.controller.mentor;

import com.capstone.ai_painter_backen.dto.TutorDto;
import com.capstone.ai_painter_backen.service.TutorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tutorapi")
@AllArgsConstructor
public class TutorController {

    TutorService tutorService;

    @PostMapping()
    public ResponseEntity<?> createTutor(@RequestBody TutorDto.PostDto postDto){
        return ResponseEntity.ok().body(tutorService.createTutor(postDto));
    }

    @GetMapping()
    public ResponseEntity<?> getTutor(@RequestParam Long tutorId){
        return ResponseEntity.ok().body(tutorService.getTutor(tutorId));
    }

    @PatchMapping()
    public ResponseEntity<?> modifyTutor(@RequestBody TutorDto.PatchDto patchDto){
        return ResponseEntity.ok().body(tutorService.modifyTutor(patchDto));
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteTutor(@RequestParam Long tutorId){
        tutorService.deleteTutor(new TutorDto.DeleteDto(tutorId));
        return ResponseEntity.ok().body("deleted tutorId:"+tutorId);
    }

}
