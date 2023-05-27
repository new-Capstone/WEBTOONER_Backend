package com.capstone.ai_painter_backen.service.image;

import com.capstone.ai_painter_backen.constant.Expression;
import com.capstone.ai_painter_backen.custom.CustomMultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;


@Service
@Slf4j
@RequiredArgsConstructor
public class ClientUtils {

    @Value("${modelServer.url}")
    private String url;

    public List<MultipartFile> requestImage(MultipartFile multipartFile, String expression) throws Exception {

        RestTemplate restTemplate = new RestTemplate();
        JSONParser parser = new JSONParser();
        List<MultipartFile> result = new ArrayList<>();
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("accept","application/json");

        String imageFileString = getBase64String(multipartFile);
        List<String> wrapper = new ArrayList<>();
        wrapper.add(imageFileString);

        JSONObject parameter = new JSONObject();

        parameter.put("prompt", expression);
        parameter.put("init_images", wrapper);


        HttpEntity<String> requestMessage = new HttpEntity<>(parameter.toJSONString(), httpHeaders);

        try {
            HttpEntity<String> response = restTemplate.postForEntity(url, requestMessage, String.class);

            JSONObject object = (JSONObject) parser.parse(response.getBody());
            JSONArray images = (JSONArray)object.get("images");

            for(int i = 0; i < images.size(); i++) {
                String image = (String) images.get(i);
                byte[] bytes = Base64.getDecoder().decode(image);

                MultipartFile customMultipartFile = new CustomMultipartFile(bytes, UUID.randomUUID() + ".png");
                result.add(customMultipartFile);
            }

            return result;
        } catch (Exception e) {
            log.info("server communication error");
            return null;
        }
    }

    private String getBase64String(MultipartFile multipartFile) throws Exception{
        byte[] bytes = multipartFile.getBytes();
        return Base64.getEncoder().encodeToString(bytes);
    }
}
