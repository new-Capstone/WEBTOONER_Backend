package com.capstone.ai_painter_backen.service.image;

import com.capstone.ai_painter_backen.constant.Expression;
import com.capstone.ai_painter_backen.custom.CustomMultipartFile;
import com.capstone.ai_painter_backen.dto.image.BeforeImageDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;


@Service
@Slf4j
@RequiredArgsConstructor
public class ClientUtils {

    @Value("${modelServer.url}")
    private final String url;

    public List<MultipartFile> requestImage(MultipartFile multipartFile) throws Exception {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        String filename = multipartFile.getOriginalFilename();

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        String imageFileString = getBase64String(multipartFile);
        //표정 BeforeImage 등록할 때, 같이 받아야함. 일단 임의설정
        String expression = Expression.HAPPY.getExpression();

        body.add("prompt", expression);
        body.add("image", imageFileString);


        HttpEntity<?> requestMessage = new HttpEntity<>(body, httpHeaders);

        HttpEntity<String> response = restTemplate.postForEntity(url, requestMessage, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        BeforeImageDto.ImageTransformDto imageTransformDto = objectMapper.readValue(response.getBody(), BeforeImageDto.ImageTransformDto.class);

        List<MultipartFile> result = new ArrayList<>();

        List<String> base64Images = imageTransformDto.getBase64Images();
        for (String base64Image : base64Images) {
            byte[] bytes = Base64.getDecoder().decode(base64Image);
            MultipartFile customMultipartFile = new CustomMultipartFile(bytes, filename + UUID.randomUUID());
            result.add(customMultipartFile);
        }

        return result;
    }

    private String getBase64String(MultipartFile multipartFile) throws Exception{
        byte[] bytes = multipartFile.getBytes();
        return Base64.getEncoder().encodeToString(bytes);
    }
}
