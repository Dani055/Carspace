package fontys.s3.Carspacebackend.controller.responses;

import fontys.s3.Carspacebackend.controller.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private UserDTO user;

    private String accessToken;
}
