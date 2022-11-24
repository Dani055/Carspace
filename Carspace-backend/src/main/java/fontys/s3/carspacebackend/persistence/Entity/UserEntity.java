package fontys.s3.carspacebackend.persistence.Entity;


import lombok.*;


import javax.persistence.*;
import java.io.Serializable;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="s3carspace_user")
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="role_id", referencedColumnName = "id", nullable = false)
    private RoleEntity role;

    @Column(name="username", nullable = false, unique = true)
    private String username;
    @Column(name="password", nullable = false)
    private String password;

    @Column(name="first_name", nullable = false)
    private String firstName;

    @Column(name="last_name", nullable = false)
    private String lastName;

    @Column(name="email", nullable = false)
    private String email;

    @Column(name="address", nullable = false)
    private String address;

    @Column(name="phone", nullable = false)
    private String phone;

//    @JsonIgnore
//    @OneToMany(mappedBy = "creator")
//    private Set<AuctionEntity> auctions = new HashSet<>();
}
