package fontys.s3.carspacebackend.persistence.repository;

import fontys.s3.carspacebackend.persistence.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IJPARoleRepository extends JpaRepository<RoleEntity, Long> {
}
