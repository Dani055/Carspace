package fontys.s3.Carspacebackend.controller.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;


@Data
@Builder
public class CommentDTO {
    private Long id;

    private String text;

    private Instant createdOn;

    private UserDTO creator;
}
