package fontys.s3.Carspacebackend.business.interfaces;

import fontys.s3.Carspacebackend.domain.Comment;

public interface ICommentRepository {
    Long saveComment(Comment comment, Long auctionId, Long userId);
}
