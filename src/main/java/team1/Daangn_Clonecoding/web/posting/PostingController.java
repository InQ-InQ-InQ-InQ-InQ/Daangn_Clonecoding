package team1.Daangn_Clonecoding.web.posting;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team1.Daangn_Clonecoding.domain.file.FileStore;
import team1.Daangn_Clonecoding.domain.file.UploadFile;
import team1.Daangn_Clonecoding.domain.posting.Posting;
import team1.Daangn_Clonecoding.domain.posting.postingrepository.PostingRepository;
import team1.Daangn_Clonecoding.web.posting.dto.PostingForm;
import team1.Daangn_Clonecoding.web.response.Success;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posting")
public class PostingController {

    private final PostingRepository postingRepository;
    private final FileStore fileStore;

    @PostMapping("/new")//TODO IOException ExceptionHandler 로 잡기
    public Success newPosting(@ModelAttribute PostingForm form) throws IOException {

        //파일 저장
        List<UploadFile> uploadFiles = fileStore.storeFiles(form.getMultipartFiles());

        Posting posting = Posting.createPosting(form.getTitle(), form.getCategory(), form.getProductName(),
                form.getProductPrice(), form.getExplains(), uploadFiles);

        postingRepository.save(posting);

        return new Success(true);
    }
}
