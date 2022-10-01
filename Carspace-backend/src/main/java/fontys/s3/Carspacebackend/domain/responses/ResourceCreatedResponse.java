package fontys.s3.Carspacebackend.domain.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResourceCreatedResponse {
    private String message;
    private Long id;
}