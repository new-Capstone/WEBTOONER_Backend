package com.capstone.ai_painter_backen.service.init;

import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.domain.mentor.CategoryEntity;
import com.capstone.ai_painter_backen.domain.mentor.CategoryTutorEntity;
import com.capstone.ai_painter_backen.domain.mentor.PortfolioEntity;
import com.capstone.ai_painter_backen.domain.mentor.TutorEntity;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;
    @PostConstruct
    @Transactional
    public void init(){
        initService.dbInit1();
    }


    @Component
    @Transactional
    @RequiredArgsConstructor
    static private class InitService{
        private final EntityManager entityManager;

        public void dbInit1(){

            CategoryEntity categoryEntity1 = createCategoryEntity("category1");
            CategoryEntity categoryEntity2 = createCategoryEntity("category2");
            CategoryEntity categoryEntity3 = createCategoryEntity("category3");
            CategoryEntity categoryEntity4 = createCategoryEntity("category4");

            entityManager.persist(categoryEntity1);
            entityManager.persist(categoryEntity2);
            entityManager.persist(categoryEntity3);
            entityManager.persist(categoryEntity4);


            UserEntity userEntity1  = UserEntity.createUserEssential("u1","유저1", "123");
            UserEntity userEntity2 =  UserEntity.createUserEssential("u2","유저2", "1234");
            UserEntity userEntity3 =  UserEntity.createUserEssential("u3","유저3", "1234");
            UserEntity userEntity4 =  UserEntity.createUserEssential("u4","유저4", "1234");

            entityManager.persist(userEntity1);
            entityManager.persist(userEntity2);
            entityManager.persist(userEntity3);
            entityManager.persist(userEntity4);

            TutorEntity tutorEntity1 = createTutorEntity("tutor1",userEntity1);
            TutorEntity tutorEntity2 = createTutorEntity("tutor2",userEntity2);
            TutorEntity tutorEntity3 = createTutorEntity("tutor3",userEntity3);
            TutorEntity tutorEntity4 = createTutorEntity("tutor4",userEntity4);

            entityManager.persist(tutorEntity1);
            entityManager.persist(tutorEntity2);
            entityManager.persist(tutorEntity3);
            entityManager.persist(tutorEntity4);

            CategoryTutorEntity categoryTutorEntity1 = createCategoryTutorEntity(categoryEntity1,tutorEntity1);
            CategoryTutorEntity categoryTutorEntity2 = createCategoryTutorEntity(categoryEntity2,tutorEntity2);
            CategoryTutorEntity categoryTutorEntity3 = createCategoryTutorEntity(categoryEntity3,tutorEntity3);
            CategoryTutorEntity categoryTutorEntity4 = createCategoryTutorEntity(categoryEntity4,tutorEntity4);

            entityManager.persist(categoryTutorEntity1);
            entityManager.persist(categoryTutorEntity2);
            entityManager.persist(categoryTutorEntity3);
            entityManager.persist(categoryTutorEntity4);


            PortfolioEntity portfolioEntity1 = createPortfolioEntity("image1",categoryEntity1,tutorEntity1);
            PortfolioEntity portfolioEntity2 = createPortfolioEntity("image2",categoryEntity1,tutorEntity2);
            PortfolioEntity portfolioEntity3 = createPortfolioEntity("image3",categoryEntity1,tutorEntity3);
            PortfolioEntity portfolioEntity4 = createPortfolioEntity("image4",categoryEntity1,tutorEntity4);

            entityManager.persist(portfolioEntity1);
            entityManager.persist(portfolioEntity2);
            entityManager.persist(portfolioEntity3);
            entityManager.persist(portfolioEntity4);

            entityManager.flush();
        }

        private CategoryEntity createCategoryEntity(String category){
            return CategoryEntity.builder()
                    .categoryName(category)
                    .build();
        }

        private CategoryTutorEntity createCategoryTutorEntity(CategoryEntity categoryEntity, TutorEntity tutorEntity){
            return CategoryTutorEntity.builder()
                    .categoryEntity(categoryEntity)
                    .tutorEntity(tutorEntity)
                    .build();
        }

        private PortfolioEntity createPortfolioEntity(String image, CategoryEntity categoryEntity, TutorEntity tutorEntity){
            return PortfolioEntity.builder()
                    .imageUri(image)
                    .category(categoryEntity)
                    .tutorEntity(tutorEntity)
                    .build();
        }

        private TutorEntity createTutorEntity(String tutorName, UserEntity user){
            return TutorEntity.builder()
                    .tutorName(tutorName)
                    .userEntity(user)
                    .portfolioEntities(new ArrayList<>())
                    .build();
        }


    }

}
