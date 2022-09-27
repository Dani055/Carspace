package fontys.s3.Carspacebackend.persistence.repository.impl;

import fontys.s3.Carspacebackend.business.interfaces.IUserRepository;
import fontys.s3.Carspacebackend.converters.UserConverter;
import fontys.s3.Carspacebackend.domain.User;
import fontys.s3.Carspacebackend.exception.ResourceNotFoundException;
import fontys.s3.Carspacebackend.persistence.Entity.UserEntity;
import fontys.s3.Carspacebackend.persistence.repository.IJPAUserRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository implements IUserRepository {
    private final IJPAUserRepository userRepository;
    public UserRepository(IJPAUserRepository repo){
        userRepository = repo;
    }

    @Override
    public User saveUser(User user){
        UserEntity entity = UserConverter.convertToEntity(user);
        return UserConverter.convertToPOJO(userRepository.save(entity));
    }
    @Override
    public User findById(Long id){
        Optional<UserEntity> ue = userRepository.findById(id);
        if(ue.isEmpty()){
            throw new ResourceNotFoundException("User", "id", id);
        }
        return UserConverter.convertToPOJO(ue.get());
    }

    @Override
    public User getUserByUsername(String username){
        Optional<UserEntity> ue = userRepository.findByUsername(username);
        if(ue.isEmpty()){
            throw new ResourceNotFoundException("User", "username", username);
        }

        return UserConverter.convertToPOJO(ue.get());
    }

}

