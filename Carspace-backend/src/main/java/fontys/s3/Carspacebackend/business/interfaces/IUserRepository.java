package fontys.s3.Carspacebackend.business.interfaces;


import fontys.s3.Carspacebackend.domain.User;

public interface IUserRepository {
     User getUserByUsername(String username);
     User findById(Long id);
     Long saveUser(User user);
}
