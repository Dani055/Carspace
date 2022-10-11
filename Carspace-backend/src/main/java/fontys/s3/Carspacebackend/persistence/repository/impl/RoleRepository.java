package fontys.s3.Carspacebackend.persistence.repository.impl;

import fontys.s3.Carspacebackend.business.interfaces.IRoleRepository;
import fontys.s3.Carspacebackend.domain.IRole;
import fontys.s3.Carspacebackend.domain.impl.AdminRole;
import fontys.s3.Carspacebackend.domain.impl.UserRole;
import fontys.s3.Carspacebackend.exception.ResourceNotFoundException;
import fontys.s3.Carspacebackend.persistence.Entity.RoleEntity;
import fontys.s3.Carspacebackend.persistence.repository.IJPARoleRepository;
import fontys.s3.Carspacebackend.persistence.repository.IJPAUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class RoleRepository implements IRoleRepository {
    private final IJPARoleRepository roleRepository;

    public IRole findById(Long id){
        Optional<RoleEntity> role = roleRepository.findById(id);
        if(role.isEmpty()){
            throw new ResourceNotFoundException("Role", "id", id);
        }
        if(role.get().getRoleName().equals("user")){
            return new UserRole("user", role.get().getId());
        }
        else{
            return new AdminRole("admin", role.get().getId());
        }
    }
}
