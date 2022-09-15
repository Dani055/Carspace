package fontys.s3.Carspacebackend.domain.impl;

import fontys.s3.Carspacebackend.domain.IRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminRole implements IRole {
    private String role;

    public AdminRole(String role){
        this.role = role;
    }
    public String getRole(){
        return role;
    }
    public boolean canAccessAuctionCRUD(){
        return true;
    }
    public boolean canAccessCommentCRUD(){
        return true;
    }
}
