package fontys.s3.carspacebackend.business.interfaces;

import fontys.s3.carspacebackend.domain.Comment;

public interface ICommentRepository {
    Comment findById(Long commentId);
    Long saveComment(Comment comment, Long auctionId, Long userId);
    Boolean deleteComment(Long commentId);
}
