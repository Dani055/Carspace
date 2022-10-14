package fontys.s3.Carspacebackend.controller.dto;

import fontys.s3.Carspacebackend.domain.IRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private Long id;

    private String role;

    private String username;


    private String firstName;

    private String lastName;

    private String email;

    private String address;

    private String phone;
}