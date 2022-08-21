package team1.Daangn_Clonecoding.domain.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import team1.Daangn_Clonecoding.web.exception.FileEmptyException;
import team1.Daangn_Clonecoding.web.exception.FileTransferException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    public UploadFile storeFile(MultipartFile multipartFile) {
        if (!multipartFile.isEmpty()) {
            String originalFilename = multipartFile.getOriginalFilename();
            String storeFilename = createStoreFilename(originalFilename);
            String fullPath = getFullPath(storeFilename);

            //IOException 잡아서 사용자 정의 Exception 으로 변경
            try {
                multipartFile.transferTo(new File(fullPath));
            } catch (IOException e) {
                throw new FileTransferException("파일 저장 오류");
            }

            return new UploadFile(originalFilename, storeFilename);
        } else throw new FileEmptyException("빈 파일 입니다.");
    }

    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) {
        if (multipartFiles == null) return new ArrayList<>(); // 사진을 저장하지 않는경우 null 방지
        List<UploadFile> resultList = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            UploadFile uploadFile = storeFile(multipartFile);
            resultList.add(uploadFile);
        }
        return resultList;
    }

    private String createStoreFilename(String originalFilename) {
        String ext = getExt(originalFilename);
        return UUID.randomUUID().toString() + ext;
    }

    private String getExt(String originalFilename) {
        int index = originalFilename.lastIndexOf(".");
        return originalFilename.substring(index);
    }
}
