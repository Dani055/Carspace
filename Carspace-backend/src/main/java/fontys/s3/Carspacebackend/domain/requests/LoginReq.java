package fontys.s3.Carspacebackend.domain.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginReq {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
