package fontys.s3.Carspacebackend.persistence.repository;

import fontys.s3.Carspacebackend.persistence.Entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IJPACommentRepository extends JpaRepository<CommentEntity, Long> {
}
