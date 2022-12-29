package fontys.s3.carspacebackend;

import fontys.s3.carspacebackend.domain.Auction;
import fontys.s3.carspacebackend.domain.Bid;
import fontys.s3.carspacebackend.domain.Comment;
import fontys.s3.carspacebackend.persistence.entity.RoleEntity;
import fontys.s3.carspacebackend.persistence.entity.UserEntity;
import fontys.s3.carspacebackend.persistence.repository.IJPARoleRepository;
import fontys.s3.carspacebackend.persistence.repository.IJPAUserRepository;
import fontys.s3.carspacebackend.persistence.repository.impl.AuctionRepository;
import fontys.s3.carspacebackend.persistence.repository.impl.BidRepository;
import fontys.s3.carspacebackend.persistence.repository.impl.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader implements ApplicationRunner {

    private IJPARoleRepository roleRepository;
    @Autowired
    private IJPAUserRepository userRepo;
    @Autowired
    private AuctionRepository auctionRepo;

    @Autowired
    private BidRepository bidRepo;

    @Autowired
    private CommentRepository commentRepo;

    @Autowired
    private Environment environment;

    @Autowired
    public DataLoader(IJPARoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void run(ApplicationArguments args) {
        if(Boolean.parseBoolean(environment.getProperty("INSERT_DATA"))){
            RoleEntity userRole = RoleEntity.builder().id(1L).roleName("user").build();
            RoleEntity adminRole = RoleEntity.builder().id(2L).roleName("admin").build();
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());
            String encodedPassword = bCryptPasswordEncoder.encode("123");
            UserEntity admin = UserEntity.builder().role(adminRole).username("admin").password(encodedPassword).email("email").firstName("ad").lastName("min").address("in the db").phone("+3111").build();
            UserEntity user = UserEntity.builder().role(userRole).username("test").password(encodedPassword).email("test@email.com").firstName("The").lastName("Tester").address("in the db").phone("+3111").build();
            UserEntity jdoe = UserEntity.builder().role(userRole).username("jdoe").password(encodedPassword).email("jdoe@mail.com").firstName("John").lastName("Doe").address("in the db").phone("+3111").build();
            roleRepository.save(userRole);
            roleRepository.save(adminRole);
            userRepo.save(admin);
            userRepo.save(user);
            userRepo.save(jdoe);

            List<String> bmwUrls = new ArrayList<>(Arrays.asList("https://unis3photos.s3.eu-west-2.amazonaws.com/semester3/330i+(2).jpg", "https://unis3photos.s3.eu-west-2.amazonaws.com/semester3/330i.jpg"));
            Bid bmwBid = Bid.builder().amount(3000.0).build();
            Comment bmwComment = Comment.builder().text("Nice bimmer").build();
            Auction bmwAuction = Auction.builder().carBrand("BMW").carModel("330i").carDesc("desc").location("Eindhoven").carYear(2002).startingPrice(2500).buyoutPrice(10000).hasSold(false).mileage(300000).startsOn(Instant.now()).endsOn(Instant.now().plus(4, ChronoUnit.DAYS)).build();

            List<String> audiUrls = new ArrayList<>(Arrays.asList("https://unis3photos.s3.eu-west-2.amazonaws.com/semester3/8l+(2).jpg", "https://unis3photos.s3.eu-west-2.amazonaws.com/semester3/8l.jpg"));
            Auction audiAuction = Auction.builder().carBrand("Audi").carModel("A3").carDesc("8L variant, 1.8T").location("Sofia").carYear(1998).startingPrice(2500).buyoutPrice(4000).hasSold(false).mileage(350000).startsOn(Instant.now().plus(1, ChronoUnit.DAYS)).endsOn(Instant.now().plus(5, ChronoUnit.DAYS)).build();

            List<String> mercUrls = new ArrayList<>(Arrays.asList("https://unis3photos.s3.eu-west-2.amazonaws.com/semester3/w210+(2).jpg", "https://unis3photos.s3.eu-west-2.amazonaws.com/semester3/w210.jpg"));
            Auction endedAuction = Auction.builder().carBrand("Mercedes").carModel("300E").carDesc("desc").location("Plovdiv").carYear(1999).startingPrice(1200).buyoutPrice(5000).hasSold(true).mileage(300000).startsOn(Instant.now().minus(5, ChronoUnit.DAYS)).endsOn(Instant.now().minus(2, ChronoUnit.DAYS)).build();
            auctionRepo.saveAuction(bmwAuction, 2L, bmwUrls);
            bidRepo.saveBid(bmwBid, 1L, 3L);
            commentRepo.saveComment(bmwComment, 1L, 3L);
            auctionRepo.saveAuction(endedAuction, 2L, mercUrls);
            auctionRepo.saveAuction(audiAuction, 3L, audiUrls);
        }
    }
}
