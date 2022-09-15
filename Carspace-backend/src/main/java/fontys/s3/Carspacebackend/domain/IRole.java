package fontys.s3.Carspacebackend.domain;

public interface IRole {
    String getRole();
    boolean canAccessAuctionCRUD();
    boolean canAccessCommentCRUD();
}
