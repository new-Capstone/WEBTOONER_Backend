package com.capstone.ai_painter_backen.service.awsS3;

import com.capstone.ai_painter_backen.dto.image.S3ImageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileServiceCRUD{

    //업로드한 이미지를 반환함.
    String findImgUrl(String fileName);

    //각각 이미지 파일들이 저장된 경로를 반환함.C
    List<S3ImageInfo> uploadMultiFileList(List<MultipartFile> multipartFiles);

    //각각 저장된 이미지의 경로를 통해서 한번에 리스트에 존재하는 모든 이미지를 삭제함.D
    List<String> deleteMultiFileList(List<String> multipartFilesURIes);

    //uri를 이용해서 해당 디렉토리에 존재하는 사진을 모두 열람함... R? 근데 이거는 스트링 타입을 이용할거기 때문에 필요없을거 같음.
    List<String> getMultiFileList(List<String> multiPartFilesURIes);

    //uri를 이용해서 사진들의 정보를 변경함.U
    void changeMultiFileListAtS3(List<String> multiParFilesURIes, List<MultipartFile> multipartFiles);

    //저장할 사진임.
    S3ImageInfo uploadMultiFile(MultipartFile multipartFile);
    //삭제할 사진의 URI를 이용해서 사진을 삭제함.
    String deleteMultiFile(String multiPartFileURI);
    //
    String getMultiPartFile(String multiPartFileName);



}
