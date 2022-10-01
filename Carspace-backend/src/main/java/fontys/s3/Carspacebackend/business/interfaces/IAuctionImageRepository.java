package fontys.s3.Carspacebackend.business.interfaces;

import fontys.s3.Carspacebackend.domain.Image;

public interface IAuctionImageRepository {
    void saveImage(Image i, Long auctionId);
}
