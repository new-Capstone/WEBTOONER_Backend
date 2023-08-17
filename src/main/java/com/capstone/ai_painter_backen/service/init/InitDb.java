package com.capstone.ai_painter_backen.service.init;

import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.domain.image.AfterImageEntity;
import com.capstone.ai_painter_backen.domain.image.BeforeImageEntity;
import com.capstone.ai_painter_backen.domain.mentor.CategoryEntity;
import com.capstone.ai_painter_backen.domain.mentor.CategoryTutorEntity;
import com.capstone.ai_painter_backen.domain.mentor.PortfolioEntity;
import com.capstone.ai_painter_backen.domain.mentor.TutorEntity;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String DDL;
    @Transactional
    @PostConstruct
    public void init(){
        if(DDL.equals("create")||DDL.equals("create-drop"))
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

            userEntity1.enrollTutor(tutorEntity1);
            userEntity2.enrollTutor(tutorEntity2);
            userEntity3.enrollTutor(tutorEntity3);
            userEntity4.enrollTutor(tutorEntity4);

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
            PortfolioEntity portfolioEntity2 = createPortfolioEntity("image2",categoryEntity2,tutorEntity2);
            PortfolioEntity portfolioEntity3 = createPortfolioEntity("image3",categoryEntity3,tutorEntity3);
            PortfolioEntity portfolioEntity4 = createPortfolioEntity("image4",categoryEntity4,tutorEntity4);

            entityManager.persist(portfolioEntity1);
            entityManager.persist(portfolioEntity2);
            entityManager.persist(portfolioEntity3);
            entityManager.persist(portfolioEntity4);

            BeforeImageEntity beforeImageEntity1 = createBeforeImageEntity("beforeImageEntity1", userEntity1);
            BeforeImageEntity beforeImageEntity2 = createBeforeImageEntity("beforeImageEntity2", userEntity2);
            BeforeImageEntity beforeImageEntity3 = createBeforeImageEntity("beforeImageEntity3", userEntity3);
            BeforeImageEntity beforeImageEntity4 = createBeforeImageEntity("beforeImageEntity4", userEntity4);

            entityManager.persist(beforeImageEntity1);
            entityManager.persist(beforeImageEntity2);
            entityManager.persist(beforeImageEntity3);
            entityManager.persist(beforeImageEntity4);


            AfterImageEntity afterImageEntity1 = createAfterImageEntity("afterImage1",beforeImageEntity1);
            AfterImageEntity afterImageEntity2 = createAfterImageEntity("afterImage2",beforeImageEntity1);

            AfterImageEntity afterImageEntity3 = createAfterImageEntity("afterImage3",beforeImageEntity2);
            AfterImageEntity afterImageEntity4 = createAfterImageEntity("afterImage4",beforeImageEntity2);

            AfterImageEntity afterImageEntity5 = createAfterImageEntity("afterImage5",beforeImageEntity3);
            AfterImageEntity afterImageEntity6 = createAfterImageEntity("afterImage6",beforeImageEntity3);

            AfterImageEntity afterImageEntity7 = createAfterImageEntity("afterImage7",beforeImageEntity4);
            AfterImageEntity afterImageEntity8 = createAfterImageEntity("afterImage8",beforeImageEntity4);

            entityManager.persist(afterImageEntity1);
            entityManager.persist(afterImageEntity2);
            entityManager.persist(afterImageEntity3);
            entityManager.persist(afterImageEntity4);
            entityManager.persist(afterImageEntity5);
            entityManager.persist(afterImageEntity6);
            entityManager.persist(afterImageEntity7);
            entityManager.persist(afterImageEntity8);


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


        private BeforeImageEntity createBeforeImageEntity(String beforeImageUri, UserEntity userEntity){
            return BeforeImageEntity.builder()
                    .beforeImageUri(beforeImageUri)
                    .userEntity(userEntity)
                    .build();
        }


        private AfterImageEntity createAfterImageEntity(String imageUri, BeforeImageEntity beforeImageEntity){
            return AfterImageEntity.builder()
                    .imageURI(imageUri)
                    .beforeImageEntity(beforeImageEntity)
                    .build();
        }


    }

}
