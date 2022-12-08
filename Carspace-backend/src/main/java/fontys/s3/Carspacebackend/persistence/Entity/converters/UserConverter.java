
package fontys.s3.carspacebackend.persistence.entity.converters;
import fontys.s3.carspacebackend.domain.IRole;
import fontys.s3.carspacebackend.domain.User;
import fontys.s3.carspacebackend.controller.dto.UserDTO;
import fontys.s3.carspacebackend.domain.impl.AdminRole;
import fontys.s3.carspacebackend.domain.impl.UserRole;

import fontys.s3.carspacebackend.persistence.entity.RoleEntity;
import fontys.s3.carspacebackend.persistence.entity.UserEntity;

public final class UserConverter {
    private UserConverter(){

    }

    public static User convertToPOJO(UserEntity u){
        if(u == null){
            return null;
        }
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
                .phone(u.getPhone())
                .build();
    }
    public static UserEntity convertToEntity(User u){
        if(u == null){
            return null;
        }
        RoleEntity role = RoleEntity.builder().roleName(u.getRole().getRole()).id(u.getRole().getRoleId()).build();
        return UserEntity.builder().id(u.getId())
                .role(role)
                .email(u.getEmail())
                .firstName(u.getFirstName())
                .lastName(u.getLastName())
                .password(u.getPassword())
                .username(u.getUsername())
                .address(u.getAddress())
                .phone(u.getPhone())
                .build();
    }

    public static UserDTO convertToDTO(User u){
        if(u == null){
            return null;
        }
        return UserDTO.builder().id(u.getId()).role(u.getRole().getRole()).username(u.getUsername()).firstName(u.getFirstName()).lastName(u.getLastName()).email(u.getEmail()).address(u.getAddress()).phone(u.getPhone()).build();
    }
}
