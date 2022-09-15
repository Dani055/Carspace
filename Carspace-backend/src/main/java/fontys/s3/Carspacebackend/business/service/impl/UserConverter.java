package fontys.s3.Carspacebackend.business.service.impl;

import fontys.s3.Carspacebackend.domain.IRole;
import fontys.s3.Carspacebackend.domain.User;
import fontys.s3.Carspacebackend.domain.impl.AdminRole;
import fontys.s3.Carspacebackend.domain.impl.UserRole;
import fontys.s3.Carspacebackend.persistence.Entity.UserEntity;

public final class UserConverter {
    private UserConverter(){

    }

    public static User convert(UserEntity u){
        IRole role;
        if(u.getRole().getRoleName().equals("user")){
            role = new UserRole("user");
        }
        else{
            role = new AdminRole("user");
        }
        return User.builder().id(u.getId())
                .role(role)
                .email(u.getEmail())
                .firstName(u.getFirstName())
                .lastName(u.getLastName())
                .password(u.getPassword())
                .username(u.getUsername())
                .address(u.getAddress())
                .build();
    }
}
