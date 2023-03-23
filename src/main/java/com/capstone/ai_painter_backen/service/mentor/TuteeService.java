package com.capstone.ai_painter_backen.service.mentor;

import com.capstone.ai_painter_backen.dto.mentor.TuteeDto.RequestDeleteDto;
import com.capstone.ai_painter_backen.dto.mentor.TuteeDto.RequestSaveDto;
import com.capstone.ai_painter_backen.dto.mentor.TuteeDto.RequestUpdateDto;
import com.capstone.ai_painter_backen.dto.mentor.TuteeDto.ResponseDto;

public interface TuteeService {
    Long saveTutee(RequestSaveDto requestSaveDto);
    void updateTutee(Long id, RequestUpdateDto requestUpdateDto);
    void deleteTutee(RequestDeleteDto requestDeleteDto);
    ResponseDto findTuteeById(Long id);
}
