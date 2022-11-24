package fontys.s3.carspacebackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class IncorrectAuctionDates extends ResponseStatusException {
    public IncorrectAuctionDates(String msg){
        super(HttpStatus.BAD_REQUEST, msg);
    }
}
