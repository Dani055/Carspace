package fontys.s3.Carspacebackend.repository;

import fontys.s3.Carspacebackend.domain.Auction;
import fontys.s3.Carspacebackend.domain.Bid;
import fontys.s3.Carspacebackend.domain.Comment;
import fontys.s3.Carspacebackend.persistence.repository.impl.AuctionRepository;
import fontys.s3.Carspacebackend.persistence.repository.impl.BidRepository;
import fontys.s3.Carspacebackend.persistence.repository.impl.CommentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

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
    void shouldSaveCommentAndAppearInGetAuction() {
        InsertTestAuctions();
        Comment savedComment = Comment.builder().text("a comment").build();

        Long commentId = commentRepo.saveComment(savedComment, 1L, 3L);
        assertNotNull(commentId);

        Auction auction = auctionRepo.getAuctionById(1L);

        //Java is autistic and hashset has no get method, so this will have to do
        for (Comment c :auction.getComments()) {
            assertEquals(commentId, c.getId());
            assertEquals(savedComment.getText(), c.getText());
            assertEquals(3L, c.getCreator().getId());
            assertTrue(c.getCreatedOn().isBefore(Instant.now()));
        }
    }
}
