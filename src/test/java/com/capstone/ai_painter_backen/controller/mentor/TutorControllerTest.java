package com.capstone.ai_painter_backen.controller.mentor;

import com.capstone.ai_painter_backen.service.mentor.TutorService;
import netscape.javascript.JSObject;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TutorControllerTest {
    @LocalServerPort
    private int localServerPort; //random 서버가 실제로 구동되고 있는 port number

    @Test
    void createTutor() {
        JSONObject tutorDetailsRequestJson  = new JSONObject();
    }

    @Test
    void getTutor() {
    }

    @Test
    void getTutors() {
    }

    @Test
    void modifyTutor() {
    }

    @Test
    void deleteTutor() {
    }

    @Test
    void getTutorsByCategory() {
    }
}