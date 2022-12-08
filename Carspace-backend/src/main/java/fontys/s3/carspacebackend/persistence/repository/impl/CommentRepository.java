package fontys.s3.carspacebackend.persistence.repository.impl;

import fontys.s3.carspacebackend.business.interfaces.ICommentRepository;

import fontys.s3.carspacebackend.persistence.entity.converters.CommentConverter;

import fontys.s3.carspacebackend.domain.Comment;
import fontys.s3.carspacebackend.exception.ResourceNotFoundException;
import fontys.s3.carspacebackend.persistence.entity.AuctionEntity;
import fontys.s3.carspacebackend.persistence.entity.CommentEntity;
import fontys.s3.carspacebackend.persistence.entity.UserEntity;

import fontys.s3.carspacebackend.persistence.repository.IJPAAuctionRepository;

import fontys.s3.carspacebackend.persistence.repository.IJPACommentRepository;
import fontys.s3.carspacebackend.persistence.repository.IJPAUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class CommentRepository implements ICommentRepository {
    private IJPACommentRepository commentRepository;
    private final IJPAUserRepository userRepository;
    private final IJPAAuctionRepository auctionRepository;

    @Override
    public Comment findById(Long commentId){
        Optional<CommentEntity> ce = commentRepository.findById(commentId);
        if(ce.isEmpty()){
            throw new ResourceNotFoundException("Comment", "id", commentId);
        }

        return CommentConverter.convertToPOJO(ce.get());

    }
    @Override
    public Long saveComment(Comment c, Long auctionId, Long userId){
        Optional<AuctionEntity> auction = auctionRepository.findById(auctionId);
        if(auction.isEmpty()){
            throw new ResourceNotFoundException("Auction", "id", auctionId);
        }
        Optional<UserEntity> creator = userRepository.findById(userId);
        if(creator.isEmpty()){
            throw new ResourceNotFoundException("User", "id", userId);
        }
        CommentEntity comment = CommentConverter.convertToEntity(c);
        comment.setAuction(auction.get());
        comment.setCreator(creator.get());
        commentRepository.save(comment);

        return comment.getId();
    }

    @Override
    public Boolean deleteComment(Long commentId){
        commentRepository.deleteById(commentId);
        return true;
    }

}
