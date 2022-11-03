package fontys.s3.Carspacebackend.business.service;

import fontys.s3.Carspacebackend.domain.User;

public interface IUserService {
    Long registerUser(User u);
    User loginUser(String username, String password);

    public User getUserById(Long id);
}
