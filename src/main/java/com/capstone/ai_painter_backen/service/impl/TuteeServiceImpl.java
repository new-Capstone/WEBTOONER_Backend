package com.capstone.ai_painter_backen.service.impl;

import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.domain.mentor.TuteeEntity;
import com.capstone.ai_painter_backen.domain.mentor.TutorEntity;
import com.capstone.ai_painter_backen.mapper.TuteeMapper;
import com.capstone.ai_painter_backen.repository.TuteeRepository;
import com.capstone.ai_painter_backen.repository.TutorRepository;
import com.capstone.ai_painter_backen.repository.UserRepository;
import com.capstone.ai_painter_backen.service.TuteeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.capstone.ai_painter_backen.controller.dto.TuteeDto.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class TuteeServiceImpl implements TuteeService {

    private final TuteeRepository tuteeRepository;
    private final TutorRepository tutorRepository;
    private final UserRepository userRepository;
    private final TuteeMapper tuteeMapper;

    @Override
    @Transactional
    public Long saveTutee(RequestSaveDto requestSaveDto) {
        // TODO :: mapper 로 작성하기
        // mapper에 repository 넣는게 맞는지 모르겠어서 일단 service 에 구현
        Optional<UserEntity> user = userRepository.findById(requestSaveDto.getUserId());
        Optional<TutorEntity> tutor = tutorRepository.findById(requestSaveDto.getTutorId());

        TuteeEntity tutee = TuteeEntity.builder()
                .userEntity(user.get())
                .tutorEntity(tutor.get())
                .build();

        user.get().addTutee(tutee);

        return tuteeRepository.save(tutee).getId();
    }

    @Override
    @Transactional
    public void updateTutee(Long id, RequestUpdateDto requestUpdateDto) {
        TuteeEntity tutee = tuteeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException());
        tutee.update(requestUpdateDto.getTutor());
    }

    @Override
    @Transactional
    public void deleteTutee(RequestDeleteDto requestDeleteDto) {
        tuteeRepository.deleteById(requestDeleteDto.getTuteeId());
    }

    @Override
    public ResponseDto findTuteeById(Long id) {
        TuteeEntity tutee = tuteeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException());

        return tuteeMapper.entityToResponseDto(tutee);
    }
}