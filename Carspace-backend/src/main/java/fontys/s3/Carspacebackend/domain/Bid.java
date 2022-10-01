package fontys.s3.Carspacebackend.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class Bid {

    private Long id;

    private Double amount;

    private Date createdOn;
}
