package com.capstone.ai_painter_backen.controller.user;

import com.amazonaws.util.RuntimeHttpUtils;
import com.capstone.ai_painter_backen.dto.UserDto;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.MultipartBodyBuilder;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerTest {

    @Value("${server.port}")
    private int serverPort;
    @LocalServerPort
    private int randomServerPort;
    private String baseUrl;
    private TestRestTemplate restTemplate;
    private String generateEmail;
    private UserDto.UserResponseDto originalUserResponseDto;



    /*
    * @BeforeAll: 테스트 클래스 내의 모든 테스트 메서드 실행 전에 한 번 호출되는 메서드를 지정하는 어노테이션입니다.
    * 주로 설정 초기화와 같은 작업에 사용됩니다. 해당 메서드는 정적(static) 메서드여야 합니다.
    */
    @BeforeAll
    public void setUp() {
        baseUrl = "http://localhost:" + randomServerPort;
        restTemplate = new TestRestTemplate();
    }

    @Test
    @DisplayName("create user")
    @Order(1)
    public void testSignUpWithFormData() throws IOException, ParseException, JSONException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        generateEmail = UUID.randomUUID().toString();

        // 기본 정보 설정
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("username", "testUser");
        builder.part("userEmail", generateEmail);
        builder.part("password", "password123");
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

        // 응답 확인
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        // 추가적인 응답 검증을 수행할 수 있습니다.
    }

    private ByteArrayResource getByteArrayResource() throws IOException {
        Path imagePath = Paths.get("src/test/resources/profile_test.png"); // 이미지 파일 경로 설정
        byte[] imageBytes = Files.readAllBytes(imagePath);
        ByteArrayResource resource = new ByteArrayResource(imageBytes);
        return resource;
    }

    @Test
    @Order(2)
    void getUser() {
        String getUrl  = baseUrl + "/user?userId="+ originalUserResponseDto.getUserId();
        ResponseEntity<UserDto.UserResponseDto> responseDtoByGet =
                restTemplate.getForEntity(getUrl, UserDto.UserResponseDto.class);
        Assertions.assertEquals(responseDtoByGet.getStatusCode(),HttpStatus.OK);
        Assertions.assertEquals(responseDtoByGet.getBody().getUserId(),originalUserResponseDto.getUserId());
    }

    @Test
    @Order(3)
    public void testModifyUser() throws IOException {

        HttpHeaders headers =new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String,Object> body = new LinkedMultiValueMap<>();
        body.add("profileImage",getByteArrayResource().getByteArray());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body,headers);
        // PATCH 요청 보내기
        ResponseEntity<UserDto.UserResponseDto> response = restTemplate.exchange(
                baseUrl+"/user" + "/edit?userId=" + originalUserResponseDto.getUserId() +
                        "&nickname=" + "newDiscription" +
                        "&password=" + "newPassword" +
                        "&description=" + "newDiscription",
                HttpMethod.POST,
                requestEntity
                , UserDto.UserResponseDto.class
        );

        // 응답 검증
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(response.getBody().getDescription(),"newDiscription");
        // 여기에 응답의 상태 코드 및 내용을 검증하는 로직 추가
    }

    @Test
    @DisplayName("create user")
    @Order(4)
    void deleteUser() {
        String deleteUrl = baseUrl + "/delete?userId="+originalUserResponseDto.getUserId();
        restTemplate.delete(deleteUrl);
    }
}