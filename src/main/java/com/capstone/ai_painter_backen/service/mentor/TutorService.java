package com.capstone.ai_painter_backen.service.mentor;

import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.domain.mentor.CategoryEntity;
import com.capstone.ai_painter_backen.domain.mentor.CategoryTutorEntity;
import com.capstone.ai_painter_backen.domain.mentor.TutorEntity;
import com.capstone.ai_painter_backen.dto.mentor.TutorDto;
import com.capstone.ai_painter_backen.exception.BusinessLogicException;
import com.capstone.ai_painter_backen.exception.ExceptionCode;
import com.capstone.ai_painter_backen.mapper.mentor.TutorMapper;
import com.capstone.ai_painter_backen.repository.UserRepository;
import com.capstone.ai_painter_backen.repository.mentor.CategoryRepository;
import com.capstone.ai_painter_backen.repository.mentor.CategoryTutorRepository;
import com.capstone.ai_painter_backen.repository.mentor.TutorRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class TutorService {

    TutorMapper tutorMapper;
    TutorRepository tutorRepository;
    UserRepository userRepository;
    CategoryRepository categoryRepository;
    CategoryTutorRepository categoryTutorRepository;

    public TutorDto.ResponseDto createTutor(TutorDto.PostDto postDto){

        TutorEntity tutorEntity = tutorMapper.tutorRequestPostDtoToTutorEntity(postDto);
        UserEntity savedUserEntity  = userRepository.findById(postDto.getUserId()).orElseThrow();
        savedUserEntity.enrollTutor(tutorEntity);//user tutor mapping

        tutorEntity.setCategoryTutorEntities(getCategoryTutorEntityList(postDto.getCategoryNames(),tutorEntity));

        return  tutorMapper.tutorEntityToTutorResponseDto(tutorRepository.save(tutorEntity));// jpa cacade 로 user 자동 저장
    }

    private List<CategoryTutorEntity> getCategoryTutorEntityList(List<String> categoryNames, TutorEntity tutorEntity){

        List<CategoryTutorEntity> categoryTutorEntities = new ArrayList<>();
        try{
            List<CategoryEntity> categoryEntityList = categoryNames.stream()
                    .map(categoryRepository::findCategoryEntityByCategoryName).collect(Collectors.toList());

            for(CategoryEntity categoryEntity: categoryEntityList){
                categoryTutorEntities.add(CategoryTutorEntity.builder()
                        .categoryEntity(categoryEntity)
                        .tutorEntity(tutorEntity)
                        .build());}

            return categoryTutorEntities;

        }catch (BusinessLogicException b){
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        }


    }


    @Transactional
    public String deleteTutor(TutorDto.DeleteDto deleteDto){
        Optional<TutorEntity> tutor = tutorRepository.findById(deleteDto.getTutorId());
        if(tutor.isPresent()){
            Long userId  = tutor.get().getUserEntity().getId();
            userRepository.findById(userId).orElseThrow().unrollTutor();//연관 관계 삭제
            tutorRepository.deleteById(deleteDto.getTutorId());
            log.info("{}: 삭제됨 !", deleteDto.getTutorId());
            return  deleteDto.toString();
        }else{
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        }
    }

    public TutorDto.ResponseDto modifyTutor( TutorDto.PatchDto patchDto){
        //기존에 리스트를 새로만들어서 삽입하는게 아니라 존재하는 리스트에 하나씩 삽입함 근데 됨.
        //고아 객체 오류를 피하기 위해서는 반드시 리스트를 clear하고 진행해야함.
        TutorEntity savedTutorEntity = tutorRepository.findById(patchDto.getTutorId()).orElseThrow();
        savedTutorEntity.getCategoryTutorEntities().clear();
        List<CategoryTutorEntity> categoryTutorEntities = getCategoryTutorEntityList(patchDto.getCategoryNames(),savedTutorEntity);

        savedTutorEntity.update(patchDto.getDescription(),categoryTutorEntities);
        tutorRepository.save(savedTutorEntity);
        TutorDto.ResponseDto responseDto = tutorMapper.tutorEntityToTutorResponseDto(savedTutorEntity);


        return responseDto;
    }

    public TutorDto.ResponseDto getTutor(Long tutorId){
        TutorDto.ResponseDto responseDto = tutorMapper.tutorEntityToTutorResponseDto(
                tutorRepository.findById(tutorId).orElseThrow());

        return responseDto;
    }
}
