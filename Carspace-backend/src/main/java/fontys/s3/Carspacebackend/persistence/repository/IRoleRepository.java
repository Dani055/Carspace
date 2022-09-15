package fontys.s3.Carspacebackend.persistence.repository;

import fontys.s3.Carspacebackend.persistence.Entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<RoleEntity, Long> {
}
