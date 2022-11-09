package fontys.s3.carspacebackend.controller;

import fontys.s3.carspacebackend.business.service.ICommentService;
import fontys.s3.carspacebackend.configuration.security.isauthenticated.IsAuthenticated;
import fontys.s3.carspacebackend.controller.requests.CreateCommentReq;
import fontys.s3.carspacebackend.controller.responses.ResourceCreatedResponse;
import fontys.s3.carspacebackend.controller.responses.ResourceDeletedResponse;
import fontys.s3.carspacebackend.domain.Comment;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/comment")
@AllArgsConstructor
public class CommentController {
    private ICommentService commentService;

    @PostMapping("/{auctionId}")
    @IsAuthenticated
    public ResponseEntity<ResourceCreatedResponse> postComment(@PathVariable Long auctionId, @RequestBody @Valid CreateCommentReq req){
        Comment c = Comment.builder().text(req.getText()).build();

        Long createdCommentId = commentService.createComment(c, auctionId);

        ResourceCreatedResponse res = ResourceCreatedResponse.builder().message("Comment created!").id(createdCommentId).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @DeleteMapping("/{commentId}")
    @IsAuthenticated
    public ResponseEntity<ResourceDeletedResponse> deleteComment(@PathVariable Long commentId){
        commentService.deleteComment(commentId);

        ResourceDeletedResponse res = ResourceDeletedResponse.builder().message("Comment deleted!").build();
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}
