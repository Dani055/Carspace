package fontys.s3.Carspacebackend.controller.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResourceChangedResponse {
    private String message;
    private Long id;
}