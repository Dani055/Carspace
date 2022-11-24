package fontys.s3.carspacebackend.business.interfaces;

import fontys.s3.carspacebackend.domain.Image;

public interface IAuctionImageRepository {
    void saveImage(Image i, Long auctionId);
}
