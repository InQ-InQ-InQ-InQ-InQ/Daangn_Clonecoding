package team1.Daangn_Clonecoding.web.posting.dto;

import lombok.Data;
import team1.Daangn_Clonecoding.domain.posting.Posting;

@Data
public class PostingResponse {

    private Long postingId;

    private String title;

    private String storeFilename;

    public PostingResponse(Posting posting) {
        String storeFilename = posting.getUploadFileEntities().get(0).getUploadFile().getStoreFilename(); //첫번 째 사진의 storeFilename
        this.postingId = posting.getId();
        this.title = posting.getTitle();
        this.storeFilename = storeFilename;
    }
}
