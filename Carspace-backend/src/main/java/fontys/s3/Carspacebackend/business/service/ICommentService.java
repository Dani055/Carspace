package fontys.s3.Carspacebackend.business.service;

import fontys.s3.Carspacebackend.domain.Comment;

public interface ICommentService {
    Long createComment(Comment c, Long auctionId);

    Boolean deleteComment(Long commentId);
}
