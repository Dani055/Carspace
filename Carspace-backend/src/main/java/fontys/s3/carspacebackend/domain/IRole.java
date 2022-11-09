package fontys.s3.carspacebackend.domain;

public interface IRole {
    String getRole();
    Long getRoleId();
    boolean canAccessAuctionCRUD();
    boolean canAccessCommentCRUD();
}
