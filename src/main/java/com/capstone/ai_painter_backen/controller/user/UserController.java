package com.capstone.ai_painter_backen.controller.user;

import com.capstone.ai_painter_backen.dto.UserDto;

import com.capstone.ai_painter_backen.dto.mentor.TutorDto;
import com.capstone.ai_painter_backen.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserService userService;


    @PostMapping("/sign-up")
    @Operation(summary = "유저 정보생성 메소드", description = "유저 기본 정보를 이용해서 유저객체 생성하는 메서드")
    @ApiResponses({@ApiResponse(responseCode = "201" ,description = "유저의 정보가 정상적으로 생성됨",
            content = @Content(schema = @Schema(implementation = UserDto.UserResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!", content = @Content),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.", content = @Content)})
    public ResponseEntity<?> signUp(@RequestBody UserDto.UserPostDto userSignUpDto) throws Exception {
        return ResponseEntity.ok().body(userService.createUser(userSignUpDto));

    }

    @PostMapping("/logout")
    @Operation(summary = "유저 로그아웃 메소드", description = "유저 이메일을 이용해서 로그아웃하는 메서드")
    @ApiResponses({@ApiResponse(responseCode = "201" ,description = "로그아웃 정상 처리됨",
            content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!", content = @Content),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.", content = @Content)})
    public ResponseEntity<?> logOut(@RequestBody UserDto.UserLogOutDto userLogOutDto ) throws Exception {
        return ResponseEntity.ok().body(userService.logOutUser(userLogOutDto));
    }

    @GetMapping("/jwt-test")
    @Operation(summary = "jwt token 확인", description = "jwt 발급 test")
    @ApiResponses({@ApiResponse(responseCode = "201" ,description = "유저의 정보가 정상적으로 로그아웃",
            content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!", content = @Content),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.", content = @Content)})
    public String jwtTest() {
        return "jwtTest 요청 성공";
    }

//    @PostMapping("/new")
//
//    public ResponseEntity<?> createUser(@RequestBody @Schema(implementation = UserDto.UserPostDto.class) UserDto.UserPostDto userPostDto){
//        return ResponseEntity.ok().body(userService.createUser(userPostDto));
//
//    }

    @GetMapping
    @Operation(summary = "유저 정보 확인", description = "유저 정보를 반환하는 메서드")
    @ApiResponses({@ApiResponse(responseCode = "201" ,description = "유저 정보 정상반환 됨",
            content = @Content(schema = @Schema(implementation = UserDto.UserResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!", content = @Content),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.", content = @Content)})
    public ResponseEntity<?> getUser(@RequestParam Long userId){
        return ResponseEntity.ok().body(userService.getUser(userId));
    }

    @PatchMapping("/edit")
    @Operation(summary = "유저 정보 수정", description = "유저 정보를 반환하는 메서드")
    @ApiResponses({@ApiResponse(responseCode = "201" ,description = "유저 정보 정상 수정딤",
            content = @Content(schema = @Schema(implementation = UserDto.UserResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!", content = @Content),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.", content = @Content)})
    public ResponseEntity<?> modifyUser(@RequestBody UserDto.UserPatchDto patchDto){
        return ResponseEntity.ok().body(userService.modifyUser(patchDto));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "유저 삭제", description = "유저 유저 삭제됨")
    @ApiResponses({@ApiResponse(responseCode = "201" ,description = "정상 삭제됨",
            content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!", content = @Content),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.", content = @Content)})
    public ResponseEntity<?> deleteUser(@RequestParam Long userId){
        userService.deleteUser(new UserDto.UserDeleteDto(userId));
        return ResponseEntity.ok().body("deleted userId:"+userId);
    }
}
