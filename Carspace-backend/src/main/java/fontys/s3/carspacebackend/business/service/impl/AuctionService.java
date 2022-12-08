package fontys.s3.carspacebackend.business.service.impl;
import fontys.s3.carspacebackend.business.interfaces.IAuctionRepository;
import fontys.s3.carspacebackend.business.interfaces.IUserRepository;
import fontys.s3.carspacebackend.business.service.IAuctionService;
import fontys.s3.carspacebackend.business.validator.IAuctionValidator;
import fontys.s3.carspacebackend.domain.AccessToken;
import fontys.s3.carspacebackend.domain.Auction;
import fontys.s3.carspacebackend.domain.AuctionFilters;
import fontys.s3.carspacebackend.domain.User;
import fontys.s3.carspacebackend.exception.AuctionHasStartedException;

import fontys.s3.carspacebackend.exception.UnauthorizedException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class AuctionService implements IAuctionService {
    private IUserRepository userRepository;
    private IAuctionRepository auctionRepository;
    private IAuctionValidator auctionValidator;
    private AccessToken requestAccessToken;

    @Transactional
    public Long createAuction(Auction auc, List<String> urls){
        auctionValidator.ValidateDatesForModification(auc);

        return auctionRepository.saveAuction(auc, requestAccessToken.getUserId(), urls);

    }

    public List<Auction> getAuctionsByCreator(Long creatorId){
        return auctionRepository.getAuctionsByCreator(creatorId);
    }

    public Auction getAuctionDetails(Long id){
        return auctionRepository.getAuctionById(id);
    }
    @Transactional
    public Long editAuction(Auction auc, List<String> urls){
        //biznis logic
        User owner = userRepository.findById(requestAccessToken.getUserId());

        Auction foundAuction = auctionRepository.getAuctionById(auc.getId());
        if(!foundAuction.isOwner(owner) && !owner.getRole().canAccessAuctionCRUD()){
            throw new UnauthorizedException("Auction");
        }


        if(foundAuction.isOwner(owner) && !owner.getRole().canAccessAuctionCRUD() && foundAuction.hasStarted()){
             throw new AuctionHasStartedException();

        }
        auctionValidator.ValidateDatesForModification(auc);

        return auctionRepository.changeAuctionInfo(auc, urls);

    }
    @Transactional
    public boolean deleteAuction(Long auctionId){
        User owner = userRepository.findById(requestAccessToken.getUserId());
        Auction foundAuction = auctionRepository.getAuctionById(auctionId);
        if(!foundAuction.isOwner(owner) && !owner.getRole().canAccessAuctionCRUD()){
            throw new UnauthorizedException("Auction");
        }

        if(foundAuction.isOwner(owner) && !owner.getRole().canAccessAuctionCRUD() && foundAuction.hasStarted()){
            throw new AuctionHasStartedException();

        }
        return auctionRepository.deleteAuction(auctionId);
    }

    public Page<Auction> getLiveAuctions(AuctionFilters filters, Pageable pageable){
        return auctionRepository.findLiveAuctionsByFilters(filters, pageable);
    }
}
