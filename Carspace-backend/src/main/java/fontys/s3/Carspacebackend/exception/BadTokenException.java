package fontys.s3.Carspacebackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BadTokenException extends ResponseStatusException {
    public BadTokenException(){
        super(HttpStatus.BAD_REQUEST, "Incorrect bearer token");
    }
}
