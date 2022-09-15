package fontys.s3.Carspacebackend.domain.impl;

import fontys.s3.Carspacebackend.domain.IRole;

public class UserRole implements IRole {
    private String role;

    public UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
    public boolean canAccessAuctionCRUD(){
        return false;
    }
    public boolean canAccessCommentCRUD(){
        return false;
    }
}
