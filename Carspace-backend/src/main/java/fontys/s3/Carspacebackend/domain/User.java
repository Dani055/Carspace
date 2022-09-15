package fontys.s3.Carspacebackend.domain;

import fontys.s3.Carspacebackend.persistence.Entity.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;

    private IRole role;

    private String username;
    private String password;

    private String firstName;

    private String lastName;

    private String email;

    private String address;

}
