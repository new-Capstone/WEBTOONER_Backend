package com.capstone.ai_painter_backen.controller.image;

import com.capstone.ai_painter_backen.custom.CustomMultipartFile;
import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.domain.image.BeforeImageEntity;
import com.capstone.ai_painter_backen.dto.Result;
import com.capstone.ai_painter_backen.dto.image.BeforeImageDto;
import com.capstone.ai_painter_backen.dto.image.S3ImageInfo;
import com.capstone.ai_painter_backen.repository.UserRepository;
import com.capstone.ai_painter_backen.repository.image.BeforeImageRepository;
import com.capstone.ai_painter_backen.service.awsS3.S3FileService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
class BeforeImageControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BeforeImageRepository beforeImageRepository;

    @Autowired
    private S3FileService s3FileService;

    @AfterEach
    void after() {
        beforeImageRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void 이미지_생성() {
        //given
        Long userId = 1L;
        String expression = "happy";
        String model = "lora";
        String gender = "male";
        String loraName = "cheese";

        ClassPathResource resource = new ClassPathResource("static/image/0760.png");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("userId", userId);
        body.add("expression", expression);
        body.add("model", model);
        body.add("gender", gender);
        body.add("loraName", loraName);
        body.add("multipartFile", new HttpEntity<>(resource, httpHeaders));

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, requestHeaders);

        //when
        long startTime = System.currentTimeMillis();

        ResponseEntity<BeforeImageDto.BeforeImageResponseDto> responseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/beforeimage",
                HttpMethod.POST,
                requestEntity,
                BeforeImageDto.BeforeImageResponseDto.class);

        long endTime = System.currentTimeMillis();
        log.info("create beforeImage api : {}", endTime - startTime);

        //then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void 변환전_이미지_가져오기() {
        //given
        UserEntity userEntity = UserEntity.createUserEssential(UUID.randomUUID().toString(), "qwer", UUID.randomUUID().toString());
        userRepository.save(userEntity);

        BeforeImageEntity beforeImageEntity = getBeforeImageEntity(userEntity);
        beforeImageRepository.save(beforeImageEntity);

        String url = "http://localhost:" + port + "/beforeimage?beforeImageId=" + beforeImageEntity.getId();

        //when
        long startTime = System.currentTimeMillis();

        ResponseEntity<BeforeImageDto.BeforeImageResponseDto> responseEntity = restTemplate.getForEntity(
                url, BeforeImageDto.BeforeImageResponseDto.class);

        long endTime = System.currentTimeMillis();
        log.info("retrieve beforeImage api : {}", endTime - startTime);

        //then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void 유저_아이디로_변환전_이미지_가져오기() {
        //given
        Long userId = 1L;
        Integer pageNo = 0;
        Integer pageSize = 10;

        UserEntity userEntity = UserEntity.createUserEssential(UUID.randomUUID().toString(), "qwer", UUID.randomUUID().toString());
        userRepository.save(userEntity);

        for (int i = 0;i < 15; i++) {
            BeforeImageEntity beforeImageEntity = getBeforeImageEntity(userEntity);
            beforeImageRepository.save(beforeImageEntity);
        }

        String url = "http://localhost:" + port + "/beforeimage/userId?userId=" + userId +
                "&pageNo=" + pageNo +
                "&pageSize=" + pageSize;

        //when
        long startTime = System.currentTimeMillis();

        ResponseEntity<Result<List<BeforeImageDto.BeforeImageResponseDto>>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Result<List<BeforeImageDto.BeforeImageResponseDto>>>() {}
        );

        long endTime = System.currentTimeMillis();
        log.info("retrieve beforeImage by userId api : {}", endTime - startTime);

        //then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody().getSize()).isEqualTo(10);
    }

    /*
        TODO : after<->before가 ManyToOne 인데 beforeImage 삭제할 때 after 이미지 있으면 문제
        말하고 수정하기
     */
    @Test
    void 변환전이미지_삭제() throws Exception{
        //given
        UserEntity userEntity = UserEntity.createUserEssential(UUID.randomUUID().toString(), "qwer", UUID.randomUUID().toString());
        userRepository.save(userEntity);

        MultipartFile multipartFile = getMultipartFile();
        S3ImageInfo s3ImageInfo = s3FileService.uploadMultiFile(multipartFile);

        BeforeImageEntity beforeImageEntity = getBeforeImageEntity(userEntity);
        beforeImageEntity.setBeforeImageUri(s3ImageInfo.getFileURI());
        beforeImageRepository.save(beforeImageEntity);

        String url = "http://localhost:" + port + "/beforeimage?beforeImageId=" + beforeImageEntity.getId();

        //when
        long startTime = System.currentTimeMillis();

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                url, String.class);

        long endTime = System.currentTimeMillis();
        log.info("delete beforeImage api : {}", endTime - startTime);

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


    private BeforeImageEntity getBeforeImageEntity(UserEntity userEntity) {
        BeforeImageEntity beforeImageEntity = BeforeImageEntity.builder()
                .beforeImageUri("testUri")
                .userEntity(userEntity)
                .build();
        return beforeImageEntity;
    }

    private MultipartFile getMultipartFile() throws IOException {
        String resourcePath = "static/image/0760.png";
        ClassPathResource classPathResource = new ClassPathResource(resourcePath);
        InputStream inputStream = classPathResource.getInputStream();

        MultipartFile multipartFile = new CustomMultipartFile(inputStream.readAllBytes(), "test.png");
        return multipartFile;
    }
}