package com.capstone.ai_painter_backen.controller.user;

import com.capstone.ai_painter_backen.dto.UserDto;

import com.capstone.ai_painter_backen.service.UserService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @PostMapping("/new")
    public ResponseEntity<?> createUser(@ModelAttribute UserDto.UserPostDto userPostDto){
        return ResponseEntity.ok().body(userService.createUser(userPostDto));

    }

    @GetMapping
    public ResponseEntity<?> getUser(@RequestParam Long userId){
        return ResponseEntity.ok().body(userService.getUser(userId));
    }

    @PatchMapping("/edit")
    public ResponseEntity<?> modifyUser(@ModelAttribute UserDto.UserPatchDto userPatchDto){
        return ResponseEntity.ok().body(userService.modifyUser(userPatchDto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestParam Long userId){
        userService.deleteUser(new UserDto.UserDeleteDto(userId));
        return ResponseEntity.ok().body("deleted userId:"+userId);
    }
}
