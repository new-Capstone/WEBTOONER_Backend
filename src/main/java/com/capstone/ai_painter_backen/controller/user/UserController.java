package com.capstone.ai_painter_backen.controller.user;

import com.capstone.ai_painter_backen.dto.UserDto;

import com.capstone.ai_painter_backen.service.UserService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @PostMapping("/new")
    public ResponseEntity<?> createUser(@RequestBody @Schema(implementation = UserDto.UserPostDto.class) UserDto.UserPostDto userPostDto){
        return ResponseEntity.ok().body(userService.createUser(userPostDto));

    }

    @GetMapping
    public ResponseEntity<?> getUser(@RequestParam Long userId){
        return ResponseEntity.ok().body(userService.getUser(userId));
    }

    @PatchMapping("/edit")
    public ResponseEntity<?> modifyUser(@RequestBody UserDto.UserPatchDto patchDto){
        return ResponseEntity.ok().body(userService.modifyUser(patchDto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestParam Long userId){
        userService.deleteUser(new UserDto.UserDeleteDto(userId));
        return ResponseEntity.ok().body("deleted userId:"+userId);
    }
}
