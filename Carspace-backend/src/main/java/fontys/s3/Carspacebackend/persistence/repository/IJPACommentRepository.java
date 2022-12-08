package fontys.s3.carspacebackend.persistence.repository;

import fontys.s3.carspacebackend.persistence.entity.CommentEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IJPACommentRepository extends JpaRepository<CommentEntity, Long> {
    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {
                    "creator"
            }
    )
    Optional<CommentEntity> findById(Long id);
}
