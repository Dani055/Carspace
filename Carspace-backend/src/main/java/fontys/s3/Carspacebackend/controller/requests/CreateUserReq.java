package fontys.s3.Carspacebackend.controller.requests;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserReq {
    @NotBlank
    @Length(min = 2, max = 50)
    private String username;

    @NotBlank
    @Length(min = 3, max = 50)
    private String password;

    @NotBlank
    @Length(min = 2, max = 50)
    private String firstName;

    @NotBlank
    @Length(min = 2, max = 50)
    private String lastName;

    @NotBlank
    @Length(min = 5, max = 50)
    private String email;

    @NotBlank
    @Length(min = 2, max = 50)
    private String address;

    @NotBlank
    @Length(min = 8, max = 50)
    private String phone;
}
