package team1.Daangn_Clonecoding.domain.posting;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team1.Daangn_Clonecoding.domain.file.UploadFile;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Posting {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostingType postingType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private String productPrice;

    private String explains;

    @Embedded
    private UploadFile uploadFile;

    private Posting(String title, Category category, String productName, String productPrice, String explains, UploadFile uploadFile) {
        this.title = title;
        this.category = category;
        this.productName = productName;
        this.productPrice = productPrice;
        this.explains = explains;
        this.uploadFile = uploadFile;
        this.postingType = PostingType.ING;
    }

    //생성 메서드
    public static Posting createPosting(String title, Category category, String productName, String productPrice, String explains, UploadFile uploadFile) {
        return new Posting(title, category, productName, productPrice, explains, uploadFile);
    }
}
