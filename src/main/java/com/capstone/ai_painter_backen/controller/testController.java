package com.capstone.ai_painter_backen.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Tag(name = "스웨거 test", description = "인증 관련 api 입니다.")
@RestController
@RequestMapping("/admin")
public class testController {
    @GetMapping("/")
    ResponseEntity<?> swaggerTest(){
        return ResponseEntity.ok().body("hello wrold !");
    }


    @GetMapping("/2")
    ResponseEntity<?> swaggerTest2(){
        return ResponseEntity.ok().body("hello wrold 2!");
    }
}
