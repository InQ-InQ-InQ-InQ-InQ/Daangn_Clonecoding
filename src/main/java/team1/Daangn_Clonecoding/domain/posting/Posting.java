package team1.Daangn_Clonecoding.domain.posting;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team1.Daangn_Clonecoding.domain.file.UploadFile;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class Posting {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long adminId;

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
    private Integer productPrice;

    private String explains;

    //값타입 UploadFileEntity 를 만들어서 해결, cascade + orphanRemoval 사용
    @OneToMany(mappedBy = "posting", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UploadFileEntity> uploadFileEntities = new ArrayList<>();

    private Posting(Long adminId, String title, Category category, String productName, Integer productPrice, String explains) {
        this.adminId = adminId;
        this.title = title;
        this.category = category;
        this.productName = productName;
        this.productPrice = productPrice;
        this.explains = explains;
        this.postingType = PostingType.ING;
    }

    //생성 메서드
    public static Posting createPosting(Long adminId, String title, Category category, String productName, Integer productPrice, String explains, List<UploadFile> uploadFiles) {

        Posting posting = new Posting(adminId, title, category, productName, productPrice, explains);
        if (uploadFiles != null) {
            for (UploadFile uploadFile : uploadFiles) {
                posting.addUploadFile(uploadFile);
            }
        }
        return posting;
    }

    //편의 메서드
    public void addUploadFile(UploadFile uploadFile) {
        UploadFileEntity uploadFileEntity = new UploadFileEntity(this, uploadFile);
        uploadFileEntities.add(uploadFileEntity);
    }
}
