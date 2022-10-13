package fontys.s3.Carspacebackend.business.service.impl;

import fontys.s3.Carspacebackend.business.interfaces.IAuctionImageRepository;
import fontys.s3.Carspacebackend.business.interfaces.IAuctionRepository;
import fontys.s3.Carspacebackend.business.interfaces.IUserRepository;
import fontys.s3.Carspacebackend.business.service.IAuctionService;
import fontys.s3.Carspacebackend.domain.Auction;
import fontys.s3.Carspacebackend.domain.User;
import fontys.s3.Carspacebackend.exception.AuctionHasStartedException;
import fontys.s3.Carspacebackend.exception.IncorrectAuctionDates;
import fontys.s3.Carspacebackend.exception.UnauthorizedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class AuctionService implements IAuctionService {
    private IUserRepository userRepository;

    private IAuctionRepository auctionRepository;

    private Timestamp addDays(Timestamp date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);// w ww.  j ava  2  s  .co m
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return new Timestamp(cal.getTime().getTime());

    }
    public AuctionService(IUserRepository userRepository, IAuctionRepository auctionRepository, IAuctionImageRepository auctionImageRepository){
        this.userRepository = userRepository;
        this.auctionRepository = auctionRepository;
    }

    @Transactional
    public Long createAuction(Auction auc, Long userId, List<String> urls){
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
        if(owner.getId() != foundAuction.getCreator().getId() && !owner.getRole().canAccessAuctionCRUD()){
            throw new UnauthorizedException("Auction");
        }

        Date date = new Date();
        Timestamp now = new Timestamp(date.getTime());
        if(owner.getId() == foundAuction.getCreator().getId() && !owner.getRole().canAccessAuctionCRUD()){
            if(now.after(foundAuction.getStartsOn())){
                throw new AuctionHasStartedException();
            }
        }
        if(auc.getStartsOn().before(now) || auc.getEndsOn().before(now)){
            throw new IncorrectAuctionDates("Dates cannot be in the past");
        }
        else if(auc.getEndsOn().before(auc.getStartsOn())){
            throw new IncorrectAuctionDates("End date is before start date");
        }
        else if(addDays(now, 1).after(auc.getStartsOn())){
            throw new IncorrectAuctionDates("Auction must start at least 1 day after today");
        }
        else if(addDays(auc.getStartsOn(), 1).after(auc.getEndsOn())){
            throw new IncorrectAuctionDates("Auction must run for at least 1 day");
        }
        Long auctionId = auctionRepository.changeAuctionInfo(auc, urls);

        return auctionId;
    }
    @Transactional
    public boolean deleteAuction(Long auctionId, Long userId){
        User owner = userRepository.findById(userId);
        Auction foundAuction = auctionRepository.getAuctionById(auctionId);
        if(owner.getId() != foundAuction.getCreator().getId() && !owner.getRole().canAccessAuctionCRUD()){
            throw new UnauthorizedException("Auction");
        }

        Date date = new Date();
        Timestamp now = new Timestamp(date.getTime());
        if(owner.getId() == foundAuction.getCreator().getId() && !owner.getRole().canAccessAuctionCRUD()){
            if(now.after(foundAuction.getStartsOn())){
                throw new AuctionHasStartedException();
            }
        }
        return auctionRepository.deleteAuction(auctionId);
    }
}
