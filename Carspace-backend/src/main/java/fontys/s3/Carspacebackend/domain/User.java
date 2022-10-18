package fontys.s3.Carspacebackend.domain;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class User {
    @Setter
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
