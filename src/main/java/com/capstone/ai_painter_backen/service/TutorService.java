package com.capstone.ai_painter_backen.service;

import com.capstone.ai_painter_backen.dto.TutorDto;
import com.capstone.ai_painter_backen.mapper.TutorMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TutorService {

    @Autowired
    TutorMapper tutorMapper;

    public TutorDto.TutorResponseDto createTutor(TutorDto.TutorRequestPostDto tutorRequestPostDto){
        return null;
    }
}
