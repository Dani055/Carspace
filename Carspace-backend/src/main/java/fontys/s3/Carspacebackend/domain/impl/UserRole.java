package fontys.s3.Carspacebackend.domain.impl;

import fontys.s3.Carspacebackend.domain.IRole;

public class UserRole implements IRole {
    private String role;
    private Long id;

    public UserRole(String role, Long id){
        this.role = role;
        this.id = id;
    }

    public String getRole(){
        return role;
    }
    public Long getRoleId(){return id;}
    public boolean canAccessAuctionCRUD(){
        return false;
    }
    public boolean canAccessCommentCRUD(){
        return false;
    }
}
