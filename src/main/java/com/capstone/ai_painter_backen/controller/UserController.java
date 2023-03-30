package com.capstone.ai_painter_backen.controller;

import com.capstone.ai_painter_backen.dto.UserDto;

import com.capstone.ai_painter_backen.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserService userService;
    @PostMapping("/createuser")
    public ResponseEntity<?> createUser(@RequestBody UserDto.PostDto postDto){
        System.out.println("createuser");
        return ResponseEntity.ok().body(userService.createUser(postDto));

    }

    @GetMapping("/getuser")
    public ResponseEntity<?> getUser(@RequestParam Long userId){
        return ResponseEntity.ok().body(userService.getUser(userId));
    }

    @PatchMapping("/modifyuser")
    public ResponseEntity<?> modifyUser(@RequestBody UserDto.PatchDto patchDto){
        return ResponseEntity.ok().body(userService.modifyUser(patchDto));
    }

    @DeleteMapping("/deleteuser")
    public ResponseEntity<?> deleteUser(@RequestParam Long userId){
        userService.deleteUser(new UserDto.DeleteDto(userId));
        return ResponseEntity.ok().body("deleted userId:"+userId);
    }
}
