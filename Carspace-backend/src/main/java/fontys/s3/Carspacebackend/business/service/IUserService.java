package fontys.s3.Carspacebackend.business.service;

import fontys.s3.Carspacebackend.domain.requests.LoginReq;
import fontys.s3.Carspacebackend.persistence.Entity.UserEntity;

public interface IUserService {
    UserEntity saveUser(UserEntity u);
    UserEntity findUserByUsername(LoginReq req);
}
