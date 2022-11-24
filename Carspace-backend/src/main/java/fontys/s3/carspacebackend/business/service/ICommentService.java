package fontys.s3.carspacebackend.business.service;

import fontys.s3.carspacebackend.domain.Comment;

public interface ICommentService {
    Long createComment(Comment c, Long auctionId);

    Boolean deleteComment(Long commentId);
}
