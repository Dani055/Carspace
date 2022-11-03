package fontys.s3.Carspacebackend.business.service.impl;

import fontys.s3.Carspacebackend.business.interfaces.IAuctionRepository;
import fontys.s3.Carspacebackend.business.interfaces.ICommentRepository;
import fontys.s3.Carspacebackend.business.interfaces.IUserRepository;
import fontys.s3.Carspacebackend.business.service.ICommentService;
import fontys.s3.Carspacebackend.domain.Auction;

import fontys.s3.Carspacebackend.domain.Comment;

import fontys.s3.Carspacebackend.exception.CannotCreateCommentException;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@AllArgsConstructor
public class CommentService implements ICommentService {
    private IUserRepository userRepository;
    private IAuctionRepository auctionRepository;
    private ICommentRepository commentRepository;

    @Transactional
    public Long createComment(Comment c, Long auctionId, Long userId){
//        User bidder = userRepository.findById(userId);
        Auction auction = auctionRepository.getAuctionById(auctionId);

        if(auction.hasEnded()){
            throw new CannotCreateCommentException("Auction has ended");
        }

        return commentRepository.saveComment(c, auctionId, userId);
    }
}
