package fontys.s3.Carspacebackend.business.interfaces;


import fontys.s3.Carspacebackend.domain.User;
import fontys.s3.Carspacebackend.persistence.Entity.UserEntity;

public interface IUserRepository {
    public User getUserByUsername(String username);
    public User findById(Long id);
    public User saveUser(User user);
}
