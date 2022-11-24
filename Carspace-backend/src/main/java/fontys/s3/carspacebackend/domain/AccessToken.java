package fontys.s3.carspacebackend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessToken {
    private String username;
    private String role;
    private Long userId;
}
