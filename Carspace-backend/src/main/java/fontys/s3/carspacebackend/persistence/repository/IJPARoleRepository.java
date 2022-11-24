package fontys.s3.carspacebackend.persistence.repository;

import fontys.s3.carspacebackend.persistence.Entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IJPARoleRepository extends JpaRepository<RoleEntity, Long> {
}
