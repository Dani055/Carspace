package fontys.s3.carspacebackend.business.service;

import fontys.s3.carspacebackend.domain.User;
import fontys.s3.carspacebackend.domain.UserWithToken;

public interface IUserService {
    Long registerUser(User u);
    UserWithToken loginUser(String username, String password);
    public User getUserByUsername(String username);
    User getUserById(Long id);
    User getUserByAccessToken();
}
