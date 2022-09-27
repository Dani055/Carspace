package fontys.s3.Carspacebackend.persistence.repository;

import fontys.s3.Carspacebackend.persistence.Entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IJPARoleRepository extends JpaRepository<RoleEntity, Long> {
}
