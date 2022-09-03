package team1.Daangn_Clonecoding.domain.posting.dto;

import lombok.Data;
import team1.Daangn_Clonecoding.domain.member.Member;
import team1.Daangn_Clonecoding.domain.posting.Category;
import team1.Daangn_Clonecoding.domain.posting.Posting;
import team1.Daangn_Clonecoding.domain.posting.PostingType;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class PostingDetailResponse {

    private boolean isAdmin;

    private String title;

    private PostingType postingType;

    private Category category;

    private Integer productPrice;

    private String explains;

    private String city;

    private List<String> storeFilenames;

    private String nickname;

    private Double mTemp;

    public PostingDetailResponse(Posting posting, Long memberId) {
        Member seller = posting.getSeller();

        this.isAdmin = (seller.getId().equals(memberId)); //현 사용자가 게시물 주인인지 확인
        this.title = posting.getTitle();
        this.postingType = posting.getPostingType();
        this.category = posting.getCategory();
        this.productPrice = posting.getProductPrice();
        this.explains = posting.getExplains();
        this.city = seller.getAddress().getCity();
        this.storeFilenames = posting.getUploadFileEntities().stream()
                .map((uploadFileEntity) -> uploadFileEntity.getUploadFile().getStoreFilename())
                .collect(Collectors.toList()); //storeFilename 으로 변환
        this.nickname = seller.getNickname();
        this.mTemp = seller.getMTemp();
    }
}
