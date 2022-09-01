package team1.Daangn_Clonecoding.web.posting.dto;

import lombok.Data;
import team1.Daangn_Clonecoding.domain.member.Member;
import team1.Daangn_Clonecoding.domain.posting.Posting;

@Data
public class PostingResponse {

    private Long postingId;

    private String title;

    private String storeFilename;

    private Integer productPrice;

    private String nickname;

    private Double mTemp;

    public PostingResponse(Posting posting, Member member) {
        if (posting.getUploadFileEntities().isEmpty()) {
            this.storeFilename = null;
        } else {
            this.storeFilename = posting.getUploadFileEntities().get(0).getUploadFile().getStoreFilename(); //첫번 째 사진의 storeFilename
        }
        this.postingId = posting.getId();
        this.title = posting.getTitle();
        this.storeFilename = storeFilename;
        this.productPrice = posting.getProductPrice();
        this.nickname = member.getNickname();
        this.mTemp = member.getMTemp();
    }
}
