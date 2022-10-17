package fontys.s3.Carspacebackend.domain;


import lombok.*;

import java.sql.Timestamp;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Comment{
    private Long id;

    private String text;

    private Timestamp createdOn;

    private User creator;
}