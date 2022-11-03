package fontys.s3.Carspacebackend.business.service.impl;

import fontys.s3.Carspacebackend.business.interfaces.IAuctionRepository;
import fontys.s3.Carspacebackend.business.interfaces.IBidRepository;
import fontys.s3.Carspacebackend.business.interfaces.IUserRepository;
import fontys.s3.Carspacebackend.business.service.IBidService;
import fontys.s3.Carspacebackend.domain.Auction;
import fontys.s3.Carspacebackend.domain.Bid;
import fontys.s3.Carspacebackend.domain.User;
import fontys.s3.Carspacebackend.exception.CannotPlaceBidException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;

@Service
@AllArgsConstructor
public class BidService implements IBidService {
    private IUserRepository userRepository;
    private IAuctionRepository auctionRepository;
    private IBidRepository bidRepository;

    @Transactional
    public Long createBid(Bid b, Long auctionId, Long userId){
        User bidder = userRepository.findById(userId);
        Auction auction = auctionRepository.getAuctionById(auctionId);
        if(!auction.hasStarted()){
            throw new CannotPlaceBidException("Auction has not started yet");
        }
        else if(auction.hasEnded()){
            throw new CannotPlaceBidException("Auction has ended");
        }
        else if(auction.isOwner(bidder)){
            throw new CannotPlaceBidException("You cannot bid on your own auction");
        }
        else if(b.getAmount() < auction.getStartingPrice()){
            throw new CannotPlaceBidException("Bid is not above the minimum amount");
        }
        else if(b.getAmount() > auction.getBuyoutPrice()){
            throw new CannotPlaceBidException("Bid is above the buyout amount");
        }
        Bid highestBid = auction.getBids().stream().max(Comparator.comparingDouble(Bid::getAmount)).orElse(Bid.builder().amount(0.0).build());
        if(b.getAmount() <= highestBid.getAmount()){
            throw new CannotPlaceBidException("Bid is less than the highest bid");
        }

        return bidRepository.saveBid(b, auctionId, userId);
    }
}
