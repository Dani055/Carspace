package fontys.s3.Carspacebackend.persistence.repository.impl;

import fontys.s3.Carspacebackend.business.interfaces.ICommentRepository;

import fontys.s3.Carspacebackend.converters.CommentConverter;

import fontys.s3.Carspacebackend.domain.Comment;
import fontys.s3.Carspacebackend.exception.ResourceNotFoundException;
import fontys.s3.Carspacebackend.persistence.Entity.AuctionEntity;

import fontys.s3.Carspacebackend.persistence.Entity.CommentEntity;
import fontys.s3.Carspacebackend.persistence.Entity.UserEntity;
import fontys.s3.Carspacebackend.persistence.repository.IJPAAuctionRepository;

import fontys.s3.Carspacebackend.persistence.repository.IJPACommentRepository;
import fontys.s3.Carspacebackend.persistence.repository.IJPAUserRepository;
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
}
