package fontys.s3.Carspacebackend.controller;

import fontys.s3.Carspacebackend.business.service.ICommentService;
import fontys.s3.Carspacebackend.controller.requests.CreateCommentReq;
import fontys.s3.Carspacebackend.controller.responses.ResourceCreatedResponse;
import fontys.s3.Carspacebackend.domain.Comment;
import fontys.s3.Carspacebackend.exception.BadTokenException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
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
    public ResponseEntity<ResourceCreatedResponse> postComment(@PathVariable Long auctionId, @RequestBody @Valid CreateCommentReq req){
        Comment c = Comment.builder().text(req.getText()).build();

        Long createdCommentId = commentService.createComment(c, auctionId);

        ResourceCreatedResponse res = ResourceCreatedResponse.builder().message("Comment created!").id(createdCommentId).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }
}
