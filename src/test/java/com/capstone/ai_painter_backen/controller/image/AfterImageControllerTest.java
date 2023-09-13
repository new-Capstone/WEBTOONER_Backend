package com.capstone.ai_painter_backen.controller.image;

import com.capstone.ai_painter_backen.custom.CustomMultipartFile;
import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.domain.image.AfterImageEntity;
import com.capstone.ai_painter_backen.domain.image.BeforeImageEntity;
import com.capstone.ai_painter_backen.dto.image.AfterImageDto;
import com.capstone.ai_painter_backen.dto.image.S3ImageInfo;
import com.capstone.ai_painter_backen.repository.UserRepository;
import com.capstone.ai_painter_backen.repository.image.AfterImageRepository;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
class AfterImageControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AfterImageRepository afterImageRepository;

    @Autowired
    private BeforeImageRepository beforeImageRepository;

    @Autowired
    private S3FileService s3FileService;

    @AfterEach
    void after() {
        afterImageRepository.deleteAll();
        beforeImageRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void 변환이미지_가져오기() {
        //given
        UserEntity userEntity = UserEntity.createUserEssential(UUID.randomUUID().toString(), "qwer", UUID.randomUUID().toString());
        userRepository.save(userEntity);

        BeforeImageEntity beforeImageEntity = getBeforeImageEntity(userEntity);
        beforeImageRepository.save(beforeImageEntity);

        AfterImageEntity afterImageEntity = getAfterImageEntity(beforeImageEntity);
        afterImageRepository.save(afterImageEntity);

        String url = "http://localhost:" + port + "/afterimage?afterImageId=" + afterImageEntity.getId();

        //when
        long startTime = System.currentTimeMillis();

        ResponseEntity<AfterImageDto.AfterImageResponseDto> responseEntity = restTemplate.getForEntity(
                url, AfterImageDto.AfterImageResponseDto.class);

        long endTime = System.currentTimeMillis();
        log.info("retrieve afterImage by userId api : {}", endTime - startTime);


        //then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    //@Test
    void postAfterImageList() {
        /*
        beforeImage에서 바로 변환되고, AfterImage와 연관관계가 setting되기 때문에 이 api를 호출할 일 없음.
         */
    }

    @Test
    void 변환_이미지_삭제() throws Exception{
        //given
        UserEntity userEntity = UserEntity.createUserEssential(UUID.randomUUID().toString(), "qwer", UUID.randomUUID().toString());
        userRepository.save(userEntity);

        BeforeImageEntity beforeImageEntity = getBeforeImageEntity(userEntity);
        beforeImageRepository.save(beforeImageEntity);

        MultipartFile multipartFile = getMultipartFile();
        S3ImageInfo s3ImageInfo = s3FileService.uploadMultiFile(multipartFile);

        AfterImageEntity afterImageEntity = AfterImageEntity.builder()
                        .beforeImageEntity(beforeImageEntity)
                                .imageURI(s3ImageInfo.getFileURI())
                                        .build();
        afterImageRepository.save(afterImageEntity);

        String url = "http://localhost:" + port + "/afterimage?afterImageId=" + afterImageEntity.getId();

        //when
        long startTime = System.currentTimeMillis();
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                null,
                String.class);

        long endTime = System.currentTimeMillis();
        log.info("delete afterImage by userId api : {}", endTime - startTime);

        //then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void 변환전_이미지로_가져오기() {
        //given
        UserEntity userEntity = UserEntity.createUserEssential(UUID.randomUUID().toString(), "qwer", UUID.randomUUID().toString());
        userRepository.save(userEntity);

        BeforeImageEntity beforeImageEntity = getBeforeImageEntity(userEntity);
        beforeImageRepository.save(beforeImageEntity);

        for (int j = 0; j < 4; j++) {
            AfterImageEntity afterImageEntity = getAfterImageEntity(beforeImageEntity);
            afterImageRepository.save(afterImageEntity);
        }

        String url = "http://localhost:" + port + "/afterimage/userId?beforeImageId=" + beforeImageEntity.getId();

        //when
        long startTime = System.currentTimeMillis();

        ResponseEntity<List<AfterImageDto.AfterImageResponseDto>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<AfterImageDto.AfterImageResponseDto>>() {}
        );

        long endTime = System.currentTimeMillis();
        log.info("retrieve afterImage by beforeImageId api : {}", endTime - startTime);

        //then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private BeforeImageEntity getBeforeImageEntity(UserEntity userEntity) {
        BeforeImageEntity beforeImageEntity = BeforeImageEntity.builder()
                .beforeImageUri("testUri")
                .userEntity(userEntity)
                .build();
        return beforeImageEntity;
    }

    private AfterImageEntity getAfterImageEntity(BeforeImageEntity beforeImageEntity) {
        return AfterImageEntity.builder()
                .beforeImageEntity(beforeImageEntity)
                .imageURI("testUri")
                .build();
    }

    private MultipartFile getMultipartFile() throws IOException {
        String resourcePath = "static/image/0760.png";
        ClassPathResource classPathResource = new ClassPathResource(resourcePath);
        InputStream inputStream = classPathResource.getInputStream();

        return new CustomMultipartFile(inputStream.readAllBytes(), "test.png");
    }
}