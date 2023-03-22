package com.capstone.ai_painter_backen.service;

import com.capstone.ai_painter_backen.controller.dto.TuteeDto;
import com.capstone.ai_painter_backen.controller.dto.TuteeDto.RequestDeleteDto;
import com.capstone.ai_painter_backen.controller.dto.TuteeDto.RequestSaveDto;
import com.capstone.ai_painter_backen.controller.dto.TuteeDto.RequestUpdateDto;
import com.capstone.ai_painter_backen.controller.dto.TuteeDto.ResponseDto;

import java.util.List;

public interface TuteeService {
    Long saveTutee(RequestSaveDto requestSaveDto);
    void updateTutee(Long id, RequestUpdateDto requestUpdateDto);
    void deleteTutee(RequestDeleteDto requestDeleteDto);
    ResponseDto findTuteeById(Long id);
}
