package fontys.s3.Carspacebackend.controller.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenericObjectResponse {
    private String message;
    private Object obj;
}
