package fontys.s3.Carspacebackend.business.service.impl;
import fontys.s3.Carspacebackend.business.interfaces.IAuctionRepository;
import fontys.s3.Carspacebackend.business.interfaces.IUserRepository;
import fontys.s3.Carspacebackend.business.service.IAuctionService;
import fontys.s3.Carspacebackend.business.validator.IAuctionValidator;
import fontys.s3.Carspacebackend.domain.Auction;
import fontys.s3.Carspacebackend.domain.User;
import fontys.s3.Carspacebackend.exception.AuctionHasStartedException;

import fontys.s3.Carspacebackend.exception.UnauthorizedException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class AuctionService implements IAuctionService {
    private IUserRepository userRepository;
    private IAuctionRepository auctionRepository;
    private IAuctionValidator auctionValidator;

    @Transactional
    public Long createAuction(Auction auc, Long userId, List<String> urls){
        auctionValidator.ValidateDatesForModification(auc);

        Long auctionId = auctionRepository.saveAuction(auc, userId, urls);

        return auctionId;
    }

    public List<Auction> getAuctions(){
        return auctionRepository.getAuctions();
    }

    public Auction getAuctionDetails(Long id){
        return auctionRepository.getAuctionById(id);
    }
    @Transactional
    public Long editAuction(Auction auc, Long userId, List<String> urls){
        //biznis logic
        User owner = userRepository.findById(userId);
        Auction foundAuction = auctionRepository.getAuctionById(auc.getId());
        if(!foundAuction.isOwner(owner) && !owner.getRole().canAccessAuctionCRUD()){
            throw new UnauthorizedException("Auction");
        }

        if(foundAuction.isOwner(owner) && !owner.getRole().canAccessAuctionCRUD()){
            if(foundAuction.hasStarted()){
                throw new AuctionHasStartedException();
            }
        }

        auctionValidator.ValidateDatesForModification(auc);

        Long auctionId = auctionRepository.changeAuctionInfo(auc, urls);

        return auctionId;
    }
    @Transactional
    public boolean deleteAuction(Long auctionId, Long userId){
        User owner = userRepository.findById(userId);
        Auction foundAuction = auctionRepository.getAuctionById(auctionId);
        if(!foundAuction.isOwner(owner) && !owner.getRole().canAccessAuctionCRUD()){
            throw new UnauthorizedException("Auction");
        }

        if(foundAuction.isOwner(owner) && !owner.getRole().canAccessAuctionCRUD()){
            if(foundAuction.hasStarted()){
                throw new AuctionHasStartedException();
            }
        }
        return auctionRepository.deleteAuction(auctionId);
    }
}
