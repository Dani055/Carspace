package fontys.s3.Carspacebackend.domain;


import lombok.*;

import java.time.Instant;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Comment{
    private Long id;

    private String text;

    private Instant createdOn;

    private User creator;
}