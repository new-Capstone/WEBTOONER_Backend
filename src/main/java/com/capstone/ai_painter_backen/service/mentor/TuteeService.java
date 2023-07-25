package com.capstone.ai_painter_backen.service.mentor;

import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.domain.mentor.TuteeEntity;
import com.capstone.ai_painter_backen.domain.mentor.TutorEntity;
import com.capstone.ai_painter_backen.dto.Result;
import com.capstone.ai_painter_backen.exception.BusinessLogicException;
import com.capstone.ai_painter_backen.exception.ExceptionCode;
import com.capstone.ai_painter_backen.mapper.mentor.TuteeMapper;
import com.capstone.ai_painter_backen.repository.mentor.TuteeRepository;
import com.capstone.ai_painter_backen.repository.mentor.TutorRepository;
import com.capstone.ai_painter_backen.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.capstone.ai_painter_backen.dto.mentor.TuteeDto.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class TuteeService {

    private final TuteeRepository tuteeRepository;
    private final TutorRepository tutorRepository;
    private final UserRepository userRepository;
    private final TuteeMapper tuteeMapper;


    @Transactional
    public TuteeResponseDto saveTutee(TuteeRequestSaveDto tuteeRequestSaveDto) {
        // TODO :: mapper 로 작성하기
        // mapper에 repository 넣는게 맞는지 모르겠어서 일단 service 에 구현
        UserEntity user = userRepository.findById(tuteeRequestSaveDto.getUserId())
                .orElseThrow(()-> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        TutorEntity tutor = tutorRepository.findById(tuteeRequestSaveDto.getTutorId()).orElseThrow(()->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        TuteeEntity tutee = TuteeEntity.builder()
                .userEntity(user)
                .tutorEntity(tutor)
                .build();

        user.enrollTutee(tutee);


        return tuteeMapper.entityToTuteeResponseDto(tuteeRepository.save(tutee));
    }


    @Transactional
    public TuteeResponseDto updateTutee(TuteeRequestUpdateDto tuteeRequestUpdateDto) {

        TuteeEntity savedTuteeEntity = tuteeRepository
                .findById(tuteeRequestUpdateDto.getId()).orElseThrow(
                        ()-> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        TutorEntity savedTutorEntity = tutorRepository.findById(tuteeRequestUpdateDto.getTutorId())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        savedTuteeEntity.update(savedTutorEntity);

        return tuteeMapper.entityToTuteeResponseDto(savedTuteeEntity);
    }


    @Transactional
    public String deleteTutee(TuteeRequestDeleteDto tuteeRequestDeleteDto) {
        TuteeEntity tutee = tuteeRepository.findById(tuteeRequestDeleteDto.getTuteeId()).orElseThrow(()->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        UserEntity userEntity = userRepository.findById(tutee.getUserEntity().getId())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        userEntity.unrollTutee();

        tuteeRepository.deleteById(tuteeRequestDeleteDto.getTuteeId());
        return tuteeRequestDeleteDto.toString();
    }


    public TuteeResponseDto findTuteeById(Long id) {
        TuteeEntity tutee = tuteeRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        return tuteeMapper.entityToTuteeResponseDto(tutee);
    }

    public Result<List<TuteeResponseDto>> findTuteeByTutorId(Long tutorId, Pageable pageable) {

            Page<TuteeEntity> tuteeEntities = tuteeRepository.findAllByTutorEntityIdWithPagination(tutorId, pageable);
            List<TuteeResponseDto> tuteeResponseDtos = tuteeEntities.stream()
                    .map(tuteeMapper::entityToTuteeResponseDto)
                    .collect(Collectors.toList());

            Result r = Result.builder()
                    .data(tuteeResponseDtos)
                    .number(tuteeEntities.getNumber())
                    .size(tuteeEntities.getSize())
                    .total(tuteeEntities.getTotalPages())
                    .build();


            return r;
    }
}
