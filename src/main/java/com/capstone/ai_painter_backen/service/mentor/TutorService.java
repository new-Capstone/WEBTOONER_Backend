package com.capstone.ai_painter_backen.service.mentor;

import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.domain.mentor.CategoryEntity;
import com.capstone.ai_painter_backen.domain.mentor.CategoryTutorEntity;
import com.capstone.ai_painter_backen.domain.mentor.PortfolioEntity;
import com.capstone.ai_painter_backen.domain.mentor.TutorEntity;
import com.capstone.ai_painter_backen.dto.mentor.TutorDto;
import com.capstone.ai_painter_backen.exception.BusinessLogicException;
import com.capstone.ai_painter_backen.exception.ExceptionCode;
import com.capstone.ai_painter_backen.mapper.mentor.TutorMapper;
import com.capstone.ai_painter_backen.repository.UserRepository;
import com.capstone.ai_painter_backen.repository.mentor.CategoryRepository;
import com.capstone.ai_painter_backen.repository.mentor.CategoryTutorRepository;
import com.capstone.ai_painter_backen.repository.mentor.TutorRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.amazonaws.services.cloudformation.model.Replacement.True;

@Service
@Slf4j
@AllArgsConstructor
public class TutorService {

    TutorMapper tutorMapper;
    TutorRepository tutorRepository;
    UserRepository userRepository;
    CategoryRepository categoryRepository;
    CategoryTutorRepository categoryTutorRepository;
    //todo 자기 자신 막는 것 설정할 것
    @Transactional
    public TutorDto.TutorResponseDto createTutor(TutorDto.TutorPostDto tutorPostDto){

        TutorEntity tutorEntity = tutorMapper.tutorRequestPostDtoToTutorEntity(tutorPostDto);
        UserEntity savedUserEntity  = userRepository.findById(tutorPostDto.getUserId()).orElseThrow();
        TutorEntity savedTutorEntity = tutorRepository.save(tutorEntity);
        savedUserEntity.enrollTutor(tutorEntity);//user tutor mapping
        userRepository.save(savedUserEntity);

        tutorEntity.setCategoryTutorEntities(
                getCategoryTutorEntityList(tutorPostDto.getCategoryNames()
                        ,tutorEntity));
        return  tutorMapper.tutorEntityToTutorResponseDto(tutorEntity);// jpa cacade 로 user 자동 저장


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
    public String deleteTutor(TutorDto.TutorDeleteDto tutorDeleteDto){
        Optional<TutorEntity> tutor = tutorRepository.findById(tutorDeleteDto.getTutorId());
        if(tutor.isPresent()){
            Long userId  = tutor.get().getUserEntity().getId();
            userRepository.findById(userId).orElseThrow().unrollTutor();//연관 관계 삭제
            tutorRepository.deleteById(tutorDeleteDto.getTutorId());
            log.info("{}: 삭제됨 !", tutorDeleteDto.getTutorId());
            return  tutorDeleteDto.toString();
        }else{
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        }
    }

    @Transactional
    public TutorDto.TutorResponseDto modifyTutor(TutorDto.TutorPatchDto tutorPatchDto){
        //기존에 리스트를 새로만들어서 삽입하는게 아니라 존재하는 리스트에 하나씩 삽입함 근데 됨.
        //고아 객체 오류를 피하기 위해서는 반드시 리스트를 clear 하고 진행해야함.
        TutorEntity savedTutorEntity = tutorRepository.findById(tutorPatchDto.getTutorId()).orElseThrow();
        savedTutorEntity.getCategoryTutorEntities().clear();
        List<CategoryTutorEntity> categoryTutorEntities = getCategoryTutorEntityList(tutorPatchDto.getCategoryNames(),savedTutorEntity);

        savedTutorEntity.update(tutorPatchDto.getDescription(),categoryTutorEntities);
        tutorRepository.save(savedTutorEntity);
        TutorDto.TutorResponseDto tutorResponseDto = tutorMapper.tutorEntityToTutorResponseDto(savedTutorEntity);

        return tutorResponseDto;
    }

    @Transactional(readOnly = true)
    public TutorDto.TutorResponseDto getTutor(Long tutorId){
        TutorDto.TutorResponseDto tutorResponseDto = tutorMapper.tutorEntityToTutorResponseDto(
                tutorRepository.findById(tutorId).orElseThrow());

        return tutorResponseDto;
    }

    @Transactional(readOnly = true)
    public List<TutorDto.TutorResponseDto> getTutors() {
        return tutorRepository.findAll().stream()
                .map(m -> tutorMapper.tutorEntityToTutorResponseDto(m))
                .collect(Collectors.toList());
    }

    public List<TutorDto.TutorResponseDtoIdAndImage> getTutorsByCategory(String categoryName) {
        List<TutorEntity> tutors = tutorRepository.findAllByCategoryName(categoryName);

        return tutors.stream()
                .map(t -> tutorMapper.tutorEntityToTutorResponseDtoIdAndImage(t))
                .collect(Collectors.toList());
    }
}
