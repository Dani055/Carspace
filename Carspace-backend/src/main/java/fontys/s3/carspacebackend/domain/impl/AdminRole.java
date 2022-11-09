package fontys.s3.carspacebackend.domain.impl;

import fontys.s3.carspacebackend.domain.IRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminRole implements IRole {
    private String role;
    private Long id;
    public AdminRole(String role, Long id){
        this.role = role;
        this.id = id;
    }
    public String getRole(){
        return role;
    }
    public Long getRoleId(){
        return id;
    }
    public boolean canAccessAuctionCRUD(){
        return true;
    }
    public boolean canAccessCommentCRUD(){
        return true;
    }
}
