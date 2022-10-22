package fontys.s3.Carspacebackend.service;

import fontys.s3.Carspacebackend.business.service.impl.CommentService;
import fontys.s3.Carspacebackend.domain.Auction;
import fontys.s3.Carspacebackend.domain.Comment;
import fontys.s3.Carspacebackend.domain.TimeHelper;
import fontys.s3.Carspacebackend.exception.CannotCreateCommentException;
import fontys.s3.Carspacebackend.exception.CannotPlaceBidException;
import fontys.s3.Carspacebackend.persistence.repository.impl.AuctionRepository;
import fontys.s3.Carspacebackend.persistence.repository.impl.BidRepository;
import fontys.s3.Carspacebackend.persistence.repository.impl.CommentRepository;
import fontys.s3.Carspacebackend.persistence.repository.impl.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
    @Mock
    private AuctionRepository auctionRepoMock;

    @Mock
    private UserRepository userRepoMock;
    @Mock
    private CommentRepository commentRepoMock;

    @InjectMocks
    private CommentService commentService;

    @Test
    void tryCreateCommentWhenAuctionHasEnded(){
        TimeHelper.EnterDebugMode();
        Instant aucEnd = Instant.parse("2022-07-09T15:30:00.00Z");
        Auction auction = Auction.builder().id(100L).carBrand("BMW").carModel("330i").carYear(2002).endsOn(aucEnd).build();

        Comment toCreate = Comment.builder().id(20L).text("a comment").build();

        when(auctionRepoMock.getAuctionById(auction.getId())).thenReturn(auction);

        Exception exception = assertThrows(CannotCreateCommentException.class, () -> {
            commentService.createComment(toCreate, auction.getId(), 50L);
        });

        String expectedMessage = "Cannot create comment. Reason: Auction has ended";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(auctionRepoMock).getAuctionById(100L);
    }

    @Test
    void createComment(){
        TimeHelper.EnterDebugMode();
        Instant aucEnd = Instant.parse("2022-07-12T15:30:00.00Z");
        Auction auction = Auction.builder().id(100L).carBrand("BMW").carModel("330i").carYear(2002).endsOn(aucEnd).build();

        Comment toCreate = Comment.builder().id(20L).text("a comment").build();

        when(auctionRepoMock.getAuctionById(auction.getId())).thenReturn(auction);
        when(commentRepoMock.saveComment(toCreate, auction.getId(), 50L)).thenReturn(123L);

        assertEquals(123L, commentService.createComment(toCreate, auction.getId(), 50L));
        verify(auctionRepoMock).getAuctionById(100L);
        verify(commentRepoMock).saveComment(toCreate, 100L, 50L);
    }
}
