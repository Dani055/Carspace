package fontys.s3.carspacebackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    private String resourceName;

    public UnauthorizedException(String resourceName) {
        super(String.format("You are not authorized to modify resource:%s", resourceName));
        this.resourceName = resourceName;
    }


}