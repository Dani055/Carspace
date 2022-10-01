package fontys.s3.Carspacebackend.domain;

import fontys.s3.Carspacebackend.persistence.Entity.RoleEntity;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;

    @Setter
    private IRole role;

    private String username;

    @Setter
    private String password;

    private String firstName;

    private String lastName;

    private String email;

    private String address;

    private String phone;

}
