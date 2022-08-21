package team1.Daangn_Clonecoding.domain.posting;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import team1.Daangn_Clonecoding.domain.file.UploadFile;
import team1.Daangn_Clonecoding.domain.posting.postingrepository.PostingRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostingTest {

    @Autowired
    private PostingRepository pr;

    @PersistenceContext
    private EntityManager em;

    @Test
    void uploadFileEntityTest() {
        UploadFile uploadFile = new UploadFile("origin.png", "store.png");

        Posting posting = Posting.createPosting(1L,"title", Category.TEST, "name", 1000, "hello",
                null);

        pr.save(posting);

        em.flush();

        //test
        posting.addUploadFile(uploadFile);

        em.flush();
    }
}