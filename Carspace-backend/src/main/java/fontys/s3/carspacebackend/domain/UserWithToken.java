package fontys.s3.carspacebackend.domain;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserWithToken {
    private User user;
    private String token;
}
