package team1.Daangn_Clonecoding.domain.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {

    @Value("file.dir")
    private String fileDir;

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    //TODO IOException ExeceptionHandler 로 잡기
    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()) {
            String originalFilename = multipartFile.getOriginalFilename();
            String storeFilename = createStoreFilename(originalFilename);
            String fullPath = getFullPath(storeFilename);

            multipartFile.transferTo(new File(fullPath));

            return new UploadFile(originalFilename, storeFilename);
        } else return null; //TODO Exception 정의하고 ExceptionHandler 로 처리하기
    }

    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
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
