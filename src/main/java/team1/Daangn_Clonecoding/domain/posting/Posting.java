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
}
