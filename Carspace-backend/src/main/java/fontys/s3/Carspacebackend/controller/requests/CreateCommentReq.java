package fontys.s3.Carspacebackend.controller.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentReq {
    @NotNull
    @NotBlank
    @Length(min = 5, max = 100)
    private String text;
}
