package fontys.s3.Carspacebackend.domain;

public interface IRole {
    String getRole();
    Long getRoleId();
    boolean canAccessAuctionCRUD();
    boolean canAccessCommentCRUD();
}
