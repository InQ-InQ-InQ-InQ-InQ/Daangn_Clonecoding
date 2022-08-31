package team1.Daangn_Clonecoding.web.posting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import team1.Daangn_Clonecoding.domain.posting.Category;

import java.util.List;

@Data
@AllArgsConstructor
public class PostingForm {

    private String title;

    private Category category;

    private Integer productPrice;

    private String explains;

    List<MultipartFile> multipartFiles;
}
