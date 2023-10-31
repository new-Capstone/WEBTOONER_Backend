package com.capstone.ai_painter_backen.service.awsS3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.capstone.ai_painter_backen.dto.image.S3ImageInfo;
import com.capstone.ai_painter_backen.exception.BusinessLogicException;
import com.capstone.ai_painter_backen.exception.ExceptionCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3FileService implements FileServiceCRUD{

    private static final Logger LOG = LoggerFactory.getLogger(S3FileService.class);

    @Autowired
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String s3BucketName;

    //    @Async
    public String findByName(String fileName) {

        LOG.info("Downloading file with name {}", fileName);
        S3ObjectInputStream image = amazonS3.getObject(s3BucketName, fileName).getObjectContent();
        String result = findImgUrl(fileName);
        return result;
    }



    @Override
    public String findImgUrl(String fileName) {
        String path =  fileName;
        try{
            String result =  amazonS3.getUrl(s3BucketName, path).toString();
            return result;
        }catch (Exception e){
            throw new BusinessLogicException(ExceptionCode.FILE_IS_NOT_EXIST_IN_BUCKET);
        }
    }

    @Override
    public List<S3ImageInfo> uploadMultiFileList(List<MultipartFile> multipartFiles) {

        List<S3ImageInfo> savedFileNamed = new ArrayList<>();

        for(MultipartFile multipartFile: multipartFiles){
            System.out.println("===================================================저장중");
            savedFileNamed.add(uploadMultiFile(multipartFile));
        }

        return savedFileNamed;
    }

    @Override
    @Transactional
    public List<String> deleteMultiFileList(List<String> multipartFilesURIes) {

        List<String> deletedFileNames  = new ArrayList<>();
        for(String fileURI: multipartFilesURIes){
            deletedFileNames.add(deleteMultiFile(fileURI));
        }
        return deletedFileNames;
    }

    @Override
    public List<String> getMultiFileList(List<String> fileNames) {//todo 파일 이름
        List<String> multiPartFileURIList = new ArrayList<>();
        for(String fileName: fileNames){
            multiPartFileURIList.add(findImgUrl(fileName));
        }
        return multiPartFileURIList;
    }

    @Override
    public void changeMultiFileListAtS3(List<String> multiParFilesURIes, List<MultipartFile> multipartFiles) {
        //new..

    }

    @Override
    //save multiPartFile and get the log
    @Async
    public S3ImageInfo uploadMultiFile(final MultipartFile multipartFile) {


        try {
            final String fileName = UUID.randomUUID() + "_" + Objects.requireNonNull(multipartFile.getOriginalFilename()).substring(multipartFile.getOriginalFilename().indexOf(".")); //change the file name
            LOG.info("Uploading file with name {}", fileName);

            InputStream inputStream = new BufferedInputStream(multipartFile.getInputStream());
            final PutObjectRequest putObjectRequest =
                    new PutObjectRequest(s3BucketName, fileName, inputStream,null); //todo metadata 에 사이즈값 추가하기
            putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);//configure upload file permission
            PutObjectResult putObjectResult = amazonS3.putObject(putObjectRequest);//now send the data to S3
            System.out.println("File " + fileName + " was uploaded.");
            String fileURI = findImgUrl(fileName);
            inputStream.close();//저장한 스트림 닫음

            return S3ImageInfo.builder().fileName(fileName).fileURI(fileURI).build();

        } catch (AmazonServiceException e) {
            LOG.error("Error {} occurred while uploading file", e.getLocalizedMessage());
            return null;
        } catch (IOException ex) {
            LOG.error("Error {} occurred while deleting temporary file", ex.getLocalizedMessage());
            return null;
        }
    }

    @Override
    @Async
    public String deleteMultiFile(String fileUri){// file uri 를 삭제에서 사용할 수 있도록 변경함.
        String fileName = extractObjectKeyFromUri(fileUri);

        if(amazonS3.doesObjectExist(s3BucketName,fileName)){
            amazonS3.deleteObject(s3BucketName, fileName);
            return fileName;
        }
        else{
            throw new BusinessLogicException(ExceptionCode.FILE_IS_NOT_EXIST_IN_BUCKET);
        }
    }

    public String extractObjectKeyFromUri(String fileUri){//file uri 에서 fileName 을 가져옴
        String fileName = fileUri.substring(59);// 59번째 변경하지 말것...
        return fileName;
    }
    @Override
    public String getMultiPartFile(String multiPartFileName) {
        return findImgUrl(multiPartFileName);
    }
}

