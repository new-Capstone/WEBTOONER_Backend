package com.capstone.ai_painter_backen.service.security;

import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.dto.UserDto;
import com.capstone.ai_painter_backen.exception.BusinessLogicException;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static com.capstone.ai_painter_backen.constant.Role.USER;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class SecurityUserInfoServiceTest {
    @Autowired
    SecurityUserInfoService securityUserInfoService;
    @LocalServerPort
    private int randomServerPort;
    private String baseUrl = "http://localhost:";
    private TestRestTemplate restTemplate;
    private String generateEmail;

    private String fixedPassword = "password123";

    private UserDto.UserResponseDto originalUserResponseDto;

    private ByteArrayResource getByteArrayResource() throws IOException {
        Path imagePath = Paths.get("src/test/resources/profile_test.png"); // 이미지 파일 경로 설정
        byte[] imageBytes = Files.readAllBytes(imagePath);
        ByteArrayResource resource = new ByteArrayResource(imageBytes);
        return resource;
    }

    @BeforeAll
    void setUp(){
        baseUrl = baseUrl + randomServerPort;
        restTemplate = new TestRestTemplate();
    }

    @Test
    @Order(1)
    void createAndLoginTest() throws IOException, JSONException {
        restTemplate  = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        generateEmail = UUID.randomUUID().toString();

        // 기본 정보 설정
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("username", "testUser");
        builder.part("userEmail", generateEmail);
        builder.part("password", fixedPassword);
        builder.part("description", "Test user description");

        // 프로필 이미지 설정
        ByteArrayResource resource = getByteArrayResource();
        //todo 이 때 주의할 점은 MultiValueMap에 파일을 첨부하기 위해서는 Resource 타입으로 파일을 추가해야한다.
        builder.part("profileImage", resource, MediaType.IMAGE_PNG)
                .header("Content-Disposition",
                        "form-data; name= profileImage; filename=profile_test.png");
        MultiValueMap<String, HttpEntity<?>> multiValueBody = builder.build();
        ResponseEntity<UserDto.UserResponseDto> responseEntity = restTemplate
                .postForEntity(baseUrl+"/user/auth/sign-up",multiValueBody, UserDto.UserResponseDto.class);
        originalUserResponseDto = responseEntity.getBody();



        restTemplate = new TestRestTemplate();

        String loginUrl = baseUrl + "/login";

        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccessControlRequestMethod(HttpMethod.POST);

        JSONObject authenticationBody = new JSONObject();
        authenticationBody.put("email", originalUserResponseDto.getUserEmail());
        authenticationBody.put("password", fixedPassword );

        HttpEntity<?> httpEntity = new HttpEntity(authenticationBody.toString(),headers);
        ResponseEntity<UserDto.UserResponseDto> answer = restTemplate.postForEntity(loginUrl, httpEntity, UserDto.UserResponseDto.class);
        originalUserResponseDto = answer.getBody();
        Assertions.assertEquals(answer.getStatusCode(),HttpStatus.OK);
        Assertions.assertEquals(answer.getBody().getUserEmail(),originalUserResponseDto.getUserEmail());
    }

    @Test
    @Order(2)

    void getUserInfoFromSecurityContextHolder() {
        UserDetails userDetails = new User(
                "nonexistentuser",
                "fakeuser" ,
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
        GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userDetails,
                        null,
                authoritiesMapper
                        .mapAuthorities(userDetails.getAuthorities())));

        // 테스트 실행 및 예외 검증
        assertThrows(BusinessLogicException.class, () -> {
            securityUserInfoService.getUserInfoFromSecurityContextHolder();
        });
    }


}