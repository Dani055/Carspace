package fontys.s3.carspacebackend.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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