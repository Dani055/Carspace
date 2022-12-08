package fontys.s3.carspacebackend.service;

import fontys.s3.carspacebackend.business.service.impl.CommentService;
import fontys.s3.carspacebackend.domain.*;
import fontys.s3.carspacebackend.domain.impl.AdminRole;
import fontys.s3.carspacebackend.domain.impl.UserRole;
import fontys.s3.carspacebackend.exception.CannotCreateCommentException;
import fontys.s3.carspacebackend.exception.CannotDeleteCommentException;
import fontys.s3.carspacebackend.persistence.repository.impl.AuctionRepository;
import fontys.s3.carspacebackend.persistence.repository.impl.CommentRepository;
import fontys.s3.carspacebackend.persistence.repository.impl.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {
    @Mock
    private AuctionRepository auctionRepoMock;

    @Mock
    private UserRepository userRepoMock;
    @Mock
    private CommentRepository commentRepoMock;

    @Mock
    private AccessToken accessToken;

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
            commentService.createComment(toCreate, auction.getId());
        });

        String expectedMessage = "Cannot create comment. Reason: Auction has ended";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(auctionRepoMock).getAuctionById(100L);

        TimeHelper.QuitDebugMode();
    }

    @Test
    void createComment(){
        TimeHelper.EnterDebugMode();
        Instant aucEnd = Instant.parse("2022-07-12T15:30:00.00Z");
        Auction auction = Auction.builder().id(100L).carBrand("BMW").carModel("330i").carYear(2002).endsOn(aucEnd).build();

        Comment toCreate = Comment.builder().id(20L).text("a comment").build();

        when(accessToken.getUserId()).thenReturn(50L);
        when(auctionRepoMock.getAuctionById(auction.getId())).thenReturn(auction);
        when(commentRepoMock.saveComment(toCreate, auction.getId(), 50L)).thenReturn(123L);

        assertEquals(123L, commentService.createComment(toCreate, auction.getId()));
        verify(auctionRepoMock).getAuctionById(100L);
        verify(commentRepoMock).saveComment(toCreate, 100L, 50L);
        verify(accessToken).getUserId();

        TimeHelper.QuitDebugMode();
    }

    @Test
    void tryDeleteCommentWhenNotOwner(){
        UserRole role = UserRole.builder().id(2L).role("user").build();
        User commentCreator = User.builder().id(10L).username("jdoe").build();
        User actor = User.builder().id(11L).username("mama").role(role).build();
        Comment toDelete = Comment.builder().id(20L).text("a comment").creator(commentCreator).build();

        when(accessToken.getUserId()).thenReturn(actor.getId());
        when(userRepoMock.findById(actor.getId())).thenReturn(actor);
        when(commentRepoMock.findById(toDelete.getId())).thenReturn(toDelete);

        Exception exception = assertThrows(CannotDeleteCommentException.class, () -> {
            commentService.deleteComment(20L);
        });

        String expectedMessage = "Cannot delete comment. Reason: You can only delete your own comments";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        verify(accessToken).getUserId();
        verify(userRepoMock).findById(actor.getId());
        verify(commentRepoMock).findById(toDelete.getId());

    }
    @Test
    void deleteCommentWhenAdmin(){
        AdminRole role = AdminRole.builder().id(1L).role("admin").build();
        User commentCreator = User.builder().id(10L).username("jdoe").build();
        User actor = User.builder().id(11L).username("mama").role(role).build();
        Comment toDelete = Comment.builder().id(20L).text("a comment").creator(commentCreator).build();

        when(accessToken.getUserId()).thenReturn(actor.getId());
        when(userRepoMock.findById(actor.getId())).thenReturn(actor);
        when(commentRepoMock.findById(toDelete.getId())).thenReturn(toDelete);
        when(commentRepoMock.deleteComment(toDelete.getId())).thenReturn(true);

        assertTrue(commentService.deleteComment(20L));
        verify(accessToken).getUserId();
        verify(userRepoMock).findById(actor.getId());
        verify(commentRepoMock).findById(toDelete.getId());
    }
    @Test
    void deleteOwnComment(){
        UserRole role = UserRole.builder().id(2L).role("user").build();
        User actor = User.builder().id(11L).username("mama").role(role).build();
        Comment toDelete = Comment.builder().id(20L).text("a comment").creator(actor).build();

        when(accessToken.getUserId()).thenReturn(actor.getId());
        when(userRepoMock.findById(actor.getId())).thenReturn(actor);
        when(commentRepoMock.findById(toDelete.getId())).thenReturn(toDelete);
        when(commentRepoMock.deleteComment(toDelete.getId())).thenReturn(true);

        assertTrue(commentService.deleteComment(20L));
        verify(accessToken).getUserId();
        verify(userRepoMock).findById(actor.getId());
        verify(commentRepoMock).findById(toDelete.getId());
    }
}
