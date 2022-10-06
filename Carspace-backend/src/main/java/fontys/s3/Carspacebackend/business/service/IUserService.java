package fontys.s3.Carspacebackend.business.service;

import fontys.s3.Carspacebackend.domain.User;
import fontys.s3.Carspacebackend.domain.requests.LoginReq;
import fontys.s3.Carspacebackend.persistence.Entity.UserEntity;

public interface IUserService {
    Long registerUser(User u);
    User loginUser(String username, String password);

    public User getUserById(Long id);
}
