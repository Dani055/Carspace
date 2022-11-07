package fontys.s3.Carspacebackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CannotDeleteCommentException extends ResponseStatusException {
    public CannotDeleteCommentException(String reason){
        super(HttpStatus.FORBIDDEN, "Cannot delete comment. Reason: " + reason);
    }
}
