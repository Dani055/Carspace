package fontys.s3.carspacebackend.repository;

import fontys.s3.carspacebackend.domain.Comment;
import fontys.s3.carspacebackend.exception.ResourceNotFoundException;
import fontys.s3.carspacebackend.persistence.repository.impl.AuctionRepository;
import fontys.s3.carspacebackend.persistence.repository.impl.CommentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CommentRepositoryTest extends RepositoryTest{
    @Autowired
    private CommentRepository commentRepo;
    @Autowired
    private AuctionRepository auctionRepo;

    @AfterEach
    void tearDown()
    {
        CleanDB();
    }

    @Transactional
    @Test
    void shouldSaveCommentAndGetInfo() {
        InsertTestAuctions();
        Comment savedComment = Comment.builder().text("a comment").build();

        Long commentId = commentRepo.saveComment(savedComment, 1L, 3L);
        assertNotNull(commentId);

        Comment foundComment = commentRepo.findById(commentId);

        assertEquals(savedComment.getText(), foundComment.getText());
        assertEquals(3L, foundComment.getCreator().getId());

    }

    @Transactional
    @Test
    void shouldSaveCommentAndDelete() {
        InsertTestAuctions();
        Comment savedComment = Comment.builder().text("a comment").build();

        Long commentId = commentRepo.saveComment(savedComment, 1L, 3L);
        assertNotNull(commentId);
        commentRepo.deleteComment(commentId);

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            Comment foundComment = commentRepo.findById(commentId);
        });

        String expectedMessage = "Comment not found with id : " + "'" + commentId + "'";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }
}
