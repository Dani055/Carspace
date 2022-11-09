package fontys.s3.carspacebackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AuctionHasStartedException extends ResponseStatusException {
    public AuctionHasStartedException(){
        super(HttpStatus.FORBIDDEN, "Cannot change auction/delete info. It has already started");
    }
}
