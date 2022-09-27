package fontys.s3.Carspacebackend.converters;

import fontys.s3.Carspacebackend.domain.IRole;
import fontys.s3.Carspacebackend.domain.User;
import fontys.s3.Carspacebackend.domain.impl.AdminRole;
import fontys.s3.Carspacebackend.domain.impl.UserRole;
import fontys.s3.Carspacebackend.persistence.Entity.RoleEntity;
import fontys.s3.Carspacebackend.persistence.Entity.UserEntity;

public final class UserConverter {
    private UserConverter(){

    }

    public static User convertToPOJO(UserEntity u){
        IRole role;
        if(u.getRole().getRoleName().equals("user")){
            role = new UserRole("user", u.getRole().getId());
        }
        else{
            role = new AdminRole("admin", u.getRole().getId());
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
    public static UserEntity convertToEntity(User u){
        RoleEntity role = RoleEntity.builder().roleName(u.getRole().getRole()).id(u.getRole().getRoleId()).build();
        return UserEntity.builder().id(u.getId())
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
