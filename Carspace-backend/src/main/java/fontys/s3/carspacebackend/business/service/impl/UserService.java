package fontys.s3.carspacebackend.business.service.impl;
import fontys.s3.carspacebackend.business.interfaces.IRoleRepository;
import fontys.s3.carspacebackend.business.interfaces.IUserRepository;
import fontys.s3.carspacebackend.business.jwt.IAccessTokenHelper;
import fontys.s3.carspacebackend.business.service.IUserService;
import fontys.s3.carspacebackend.domain.AccessToken;
import fontys.s3.carspacebackend.domain.IRole;
import fontys.s3.carspacebackend.domain.User;
import fontys.s3.carspacebackend.domain.UserWithToken;
import fontys.s3.carspacebackend.exception.IncorrectCredentialsException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@AllArgsConstructor
public class UserService implements IUserService {
    private IRoleRepository roleRepository;
    private IUserRepository userRepository;

    private IAccessTokenHelper accessTokenHelper;

    private AccessToken requestAccessToken;

    @Override
    public Long registerUser(User u){
        IRole role = roleRepository.findById(1L);
        u.setRole(role);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());
        String encodedPassword = bCryptPasswordEncoder.encode(u.getPassword());
        u.setPassword(encodedPassword);
        return userRepository.saveUser(u);
    }

    @Override
    public UserWithToken loginUser(String username, String password){
        User found = userRepository.getUserByUsername(username);

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());

        if(!bCryptPasswordEncoder.matches(password, found.getPassword())){
            throw new IncorrectCredentialsException();
        }
        String accesstoken = accessTokenHelper.generateAccessToken(found);
        UserWithToken uwt = UserWithToken.builder().user(found).token(accesstoken).build();
        return uwt;
    }

    @Override
    public User getUserById(Long id){
        User found = userRepository.findById(id);

        return found;
    }
    @Override
    public User getUserByUsername(String username){
        User found = userRepository.getUserByUsername(username);
        return found;
    }
    @Override
    public User getUserByAccessToken(){
        User found = userRepository.findById(requestAccessToken.getUserId());

        return found;
    }
}
