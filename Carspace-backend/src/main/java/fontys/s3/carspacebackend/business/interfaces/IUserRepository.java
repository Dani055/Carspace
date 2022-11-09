package fontys.s3.carspacebackend.business.interfaces;


import fontys.s3.carspacebackend.domain.User;

public interface IUserRepository {
     User getUserByUsername(String username);
     User findById(Long id);
     Long saveUser(User user);
}
