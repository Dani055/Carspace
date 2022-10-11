package fontys.s3.Carspacebackend.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;


@Data
@Builder
public class CommentDTO {
    private Long id;

    private String text;

    private Timestamp createdOn;

    private UserDTO creator;
}
