package fontys.s3.carspacebackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CannotCreateCommentException extends ResponseStatusException {
    public CannotCreateCommentException(String reason){
        super(HttpStatus.FORBIDDEN, "Cannot create comment. Reason: " + reason);
    }
}
