package fontys.s3.carspacebackend.persistence.repository;
import fontys.s3.carspacebackend.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IJPAUserRepository extends JpaRepository<UserEntity, Long> {
    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {
                    "role"
            }
    )
    Optional<UserEntity> findByUsername(String username);
}
