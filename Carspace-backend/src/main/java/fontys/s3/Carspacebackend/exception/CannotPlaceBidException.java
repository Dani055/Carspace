package fontys.s3.Carspacebackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CannotPlaceBidException extends ResponseStatusException {
    public CannotPlaceBidException(String reason){
        super(HttpStatus.FORBIDDEN, "Cannot place bid. Reason: " + reason);
    }
}
