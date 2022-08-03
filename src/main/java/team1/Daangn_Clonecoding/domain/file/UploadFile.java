package team1.Daangn_Clonecoding.domain.file;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Getter @Setter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadFile {

    private String originalFilename;
    private String storeFilename;

    public UploadFile(String originalFilename, String storeFilename) {
        this.originalFilename = originalFilename;
        this.storeFilename = storeFilename;
    }
}
