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
import java.util.concurrent.ConcurrentHashMap;


@Service
@Slf4j
@RequiredArgsConstructor
public class ClientUtils {

    @Value("${modelServer.pixarUrl}")
    private String pixarUrl;

    @Value("${modelServer.loraUrl}")
    private String loraUrl;

    private final String defaultNegativePrompt = "lowres, bad anatomy, bad hands, text, error, missing fingers, extra digit, fewer digits, cropped, worst quality, low quality, normal quality, jpeg artifacts, signature, watermark, username, blurry, artist name";
    private final String defaultPrompt = "masterpiece, best quality, ";
    private final int defaultBatchSize = 2;

    private final int steps = 20;

    private final ConcurrentHashMap<String, String> hashMap = new ConcurrentHashMap<>();

    public List<MultipartFile> requestImage(MultipartFile multipartFile, String expression,
                                            String model, String gender, String modelName) throws Exception {

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

        try {
            if (model.equals("lora")) {

                parameter.put("prompt", "<lora:" + modelName + ":1>, " + "(" + expression + ":1.5)" + ", white_background");
                parameter.put("batch_size", defaultBatchSize);
                parameter.put("init_images", wrapper);
                parameter.put("steps", steps);
                parameter.put("denoising_strength", 0.5);
                parameter.put("cfg_scale", 6);
                log.info("prompt : {}", "<lora:" + modelName + ":1>, " + "(" + expression + ":1.5)" + ", white_background");


                HttpEntity<String> requestMessage = new HttpEntity<>(parameter.toJSONString(), httpHeaders);
                HttpEntity<String> response = restTemplate.postForEntity(loraUrl, requestMessage, String.class);

                JSONObject object = (JSONObject) parser.parse(response.getBody());
                JSONArray images = (JSONArray)object.get("images");

                for(int i = 0; i < images.size(); i++) {
                    String image = (String) images.get(i);
                    byte[] bytes = Base64.getDecoder().decode(image);

                    MultipartFile customMultipartFile = new CustomMultipartFile(bytes, UUID.randomUUID() + ".png");
                    result.add(customMultipartFile);
                }

                return result;
            } else if (model.equals("pixar")){

                log.info("internal pixar");
                parameter.put("prompt", defaultPrompt + ", 1" + gender + ", " + expression + ", face");
                parameter.put("negative_prompt", defaultNegativePrompt);
                parameter.put("batch_size", defaultBatchSize);
                parameter.put("init_images", wrapper);
                parameter.put("steps", steps);

                HttpEntity<String> requestMessage = new HttpEntity<>(parameter.toJSONString(), httpHeaders);



                HttpEntity<String> response = restTemplate.postForEntity(pixarUrl, requestMessage, String.class);

                JSONObject object = (JSONObject) parser.parse(response.getBody());
                JSONArray images = (JSONArray)object.get("images");

                for(int i = 0; i < images.size(); i++) {
                    String image = (String) images.get(i);
                    byte[] bytes = Base64.getDecoder().decode(image);
                    MultipartFile customMultipartFile = new CustomMultipartFile(bytes, UUID.randomUUID() + ".png");
                    result.add(customMultipartFile);
                }

                return result;
            } else {
                log.info("invalid model name error");
                throw new Exception();
            }

        } catch (Exception e) {
            throw new Exception();
        }
    }

    private String getBase64String(MultipartFile multipartFile) throws Exception{
        byte[] bytes = multipartFile.getBytes();
        return Base64.getEncoder().encodeToString(bytes);
    }
}
