package fontys.s3.carspacebackend;

import fontys.s3.carspacebackend.domain.Auction;
import fontys.s3.carspacebackend.persistence.entity.RoleEntity;
import fontys.s3.carspacebackend.persistence.entity.UserEntity;
import fontys.s3.carspacebackend.persistence.repository.IJPARoleRepository;
import fontys.s3.carspacebackend.persistence.repository.IJPAUserRepository;
import fontys.s3.carspacebackend.persistence.repository.impl.AuctionRepository;
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
import java.util.List;

@Component
public class DataLoader implements ApplicationRunner {

    private IJPARoleRepository roleRepository;
    @Autowired
    private IJPAUserRepository userRepo;
    @Autowired
    private AuctionRepository auctionRepo;

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
            UserEntity user = UserEntity.builder().role(userRole).username("test").password(encodedPassword).email("email").firstName("The").lastName("Tester").address("in the db").phone("+3111").build();
            roleRepository.save(userRole);
            roleRepository.save(adminRole);
            userRepo.save(admin);
            userRepo.save(user);
            List<String> urls = new ArrayList<>();
            Auction runningAuction = Auction.builder().carBrand("BMW").carModel("330i").carDesc("desc").location("Eindhoven").carYear(2002).startingPrice(1000).buyoutPrice(2000).hasSold(false).mileage(300000).startsOn(Instant.now()).endsOn(Instant.now().plus(4, ChronoUnit.DAYS)).build();
            Auction endedAuction = Auction.builder().carBrand("Mercedes").carModel("300E").carDesc("desc").location("Plovdiv").carYear(1999).startingPrice(1200).buyoutPrice(5000).hasSold(true).mileage(300000).startsOn(Instant.now().minus(5, ChronoUnit.DAYS)).endsOn(Instant.now().minus(2, ChronoUnit.DAYS)).build();
            auctionRepo.saveAuction(runningAuction, 1L, urls);
            auctionRepo.saveAuction(endedAuction, 1L, urls);
        }
    }
}
