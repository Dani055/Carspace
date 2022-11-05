package fontys.s3.Carspacebackend.business.service;

import fontys.s3.Carspacebackend.domain.User;
import fontys.s3.Carspacebackend.domain.UserWithToken;

public interface IUserService {
    Long registerUser(User u);
    UserWithToken loginUser(String username, String password);

    User getUserById(Long id);
    User getUserByAccessToken();
}
