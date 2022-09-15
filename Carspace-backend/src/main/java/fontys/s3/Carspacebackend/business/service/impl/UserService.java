package fontys.s3.Carspacebackend.business.service.impl;
import fontys.s3.Carspacebackend.business.service.IUserService;
import fontys.s3.Carspacebackend.domain.User;
import fontys.s3.Carspacebackend.domain.requests.LoginReq;
import fontys.s3.Carspacebackend.exception.IncorrectCredentialsException;
import fontys.s3.Carspacebackend.exception.ResourceNotFoundException;
import fontys.s3.Carspacebackend.persistence.Entity.RoleEntity;
import fontys.s3.Carspacebackend.persistence.Entity.UserEntity;
import fontys.s3.Carspacebackend.persistence.repository.IRoleRepository;
import fontys.s3.Carspacebackend.persistence.repository.IUserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    private IRoleRepository roleRepository;
    private IUserRepository userRepository;

    public UserService(IRoleRepository roleRepository, IUserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity saveUser(UserEntity u){
        RoleEntity role = roleRepository.findById(1L).get();
        u.setRole(role);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());
        String encodedPassword = bCryptPasswordEncoder.encode(u.getPassword());
        u.setPassword(encodedPassword);
        return userRepository.save(u);
    }

    @Override
    public UserEntity findUserByUsername(LoginReq req){
        Optional<UserEntity> found = userRepository.findByUsername(req.getUsername());
        if(found.isEmpty()){
            throw new ResourceNotFoundException("User", "username", req.getUsername());
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());

        if(!bCryptPasswordEncoder.matches(req.getPassword(), found.get().getPassword())){
            throw new IncorrectCredentialsException();
        }

        return found.get();
    }
}
