package fontys.s3.Carspacebackend.business.service.impl;

import fontys.s3.Carspacebackend.business.interfaces.IAuctionRepository;
import fontys.s3.Carspacebackend.business.interfaces.ICommentRepository;
import fontys.s3.Carspacebackend.business.interfaces.IUserRepository;
import fontys.s3.Carspacebackend.business.service.ICommentService;
import fontys.s3.Carspacebackend.domain.AccessToken;
import fontys.s3.Carspacebackend.domain.Auction;

import fontys.s3.Carspacebackend.domain.Comment;

import fontys.s3.Carspacebackend.domain.User;
import fontys.s3.Carspacebackend.exception.CannotCreateCommentException;

import fontys.s3.Carspacebackend.exception.CannotDeleteCommentException;
import fontys.s3.Carspacebackend.persistence.repository.impl.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@AllArgsConstructor
public class CommentService implements ICommentService {
    private IUserRepository userRepository;
    private IAuctionRepository auctionRepository;
    private ICommentRepository commentRepository;

    private AccessToken requestAccessToken;

    @Transactional
    public Long createComment(Comment c, Long auctionId){
        Auction auction = auctionRepository.getAuctionById(auctionId);

        if(auction.hasEnded()){
            throw new CannotCreateCommentException("Auction has ended");
        }

        return commentRepository.saveComment(c, auctionId, requestAccessToken.getUserId());
    }

    @Transactional
    public Boolean deleteComment(Long commentId){
        User u = userRepository.findById(requestAccessToken.getUserId());
        Comment c = commentRepository.findById(commentId);
        if(c.getCreator().getId() != u.getId()){
            if(!u.getRole().canAccessCommentCRUD()){
                throw new CannotDeleteCommentException("You can only delete your own comments");
            }
        }

        return commentRepository.deleteComment(commentId);
    }
}
