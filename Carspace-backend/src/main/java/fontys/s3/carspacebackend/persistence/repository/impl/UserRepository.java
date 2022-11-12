package fontys.s3.carspacebackend.persistence.repository.impl;

import fontys.s3.carspacebackend.business.interfaces.IUserRepository;
import fontys.s3.carspacebackend.converters.UserConverter;
import fontys.s3.carspacebackend.domain.User;
import fontys.s3.carspacebackend.exception.IncorrectCredentialsException;
import fontys.s3.carspacebackend.exception.ResourceNotFoundException;
import fontys.s3.carspacebackend.exception.UsernameExistsException;
import fontys.s3.carspacebackend.persistence.Entity.UserEntity;
import fontys.s3.carspacebackend.persistence.repository.IJPAUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserRepository implements IUserRepository {
    private final IJPAUserRepository userRepository;

    @Override
    public Long saveUser(User user){
        UserEntity entity = UserConverter.convertToEntity(user);
        try{
            return userRepository.save(entity).getId();
        }
        catch (DataIntegrityViolationException ex){
            throw new UsernameExistsException();
        }

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
            throw new IncorrectCredentialsException();
        }

        return UserConverter.convertToPOJO(ue.get());
    }

}

