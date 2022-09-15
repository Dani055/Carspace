package fontys.s3.Carspacebackend.persistence.repository;

import fontys.s3.Carspacebackend.persistence.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
}
