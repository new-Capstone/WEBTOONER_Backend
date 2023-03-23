package com.capstone.ai_painter_backen.service.mentor;

import com.capstone.ai_painter_backen.domain.mentor.TutorEntity;
import com.capstone.ai_painter_backen.dto.TutorDto;
import com.capstone.ai_painter_backen.mapper.TutorMapper;
import com.capstone.ai_painter_backen.repository.mentor.TutorRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class TutorService {

    TutorMapper tutorMapper;
    TutorRepository tutorRepository;

    public TutorDto.ResponseDto createTutor(TutorDto.PostDto postDto){
        TutorEntity tutorEntity = tutorMapper.tutorRequestPostDtoToTutorEntity(postDto);
        return  tutorMapper.tutorEntityToTutorResponseDto(tutorRepository.save(tutorEntity));
    }

    public void deleteTutor(TutorDto.DeleteDto deleteDto){
        tutorRepository.deleteById(deleteDto.getTutorId());
        log.info("{}: 삭제됨 !", deleteDto.getTutorId());
    }

    @Transactional
    public TutorDto.ResponseDto modifyTutor(TutorDto.PatchDto patchDto){
        TutorEntity savedTutorEntity = tutorRepository.findById(patchDto.getTutorId()).orElseThrow();
        savedTutorEntity.update(patchDto.getDescription(), patchDto.getCategoryTutorEntities()); //todo category 구현되고 나면 수정 필요
        TutorDto.ResponseDto responseDto = tutorMapper.tutorEntityToTutorResponseDto(savedTutorEntity);

        return responseDto;
    }

    public TutorDto.ResponseDto getTutor(Long tutorId){
        TutorDto.ResponseDto responseDto = tutorMapper.tutorEntityToTutorResponseDto(
                tutorRepository.findById(tutorId).orElseThrow());

        return responseDto;
    }
}
