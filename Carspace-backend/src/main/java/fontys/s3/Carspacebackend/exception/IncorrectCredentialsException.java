package fontys.s3.Carspacebackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class IncorrectCredentialsException extends ResponseStatusException {
    public IncorrectCredentialsException(){
        super(HttpStatus.UNAUTHORIZED, "Incorrect username or password");
    }
}
