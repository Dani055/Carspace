package fontys.s3.carspacebackend.repository;

import fontys.s3.carspacebackend.domain.TimeHelper;
import fontys.s3.carspacebackend.persistence.Entity.AuctionEntity;
import fontys.s3.carspacebackend.persistence.Entity.ImageEntity;
import fontys.s3.carspacebackend.persistence.Entity.RoleEntity;
import fontys.s3.carspacebackend.persistence.Entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;


public abstract class RepositoryTest {
    @Autowired
    private
    EntityManager entityManager;

    protected void InsertTestRoles(){
        RoleEntity user = RoleEntity.builder().roleName("user").build();
        RoleEntity admin = RoleEntity.builder().roleName("admin").build();
        entityManager.persist(user);
        entityManager.persist(admin);
        System.out.println("Roles inserted");
    }
    protected void InsertTestUsers(){
        InsertTestRoles();
        RoleEntity userrole = entityManager.find(RoleEntity.class, 1L);
        RoleEntity adminrole = entityManager.find(RoleEntity.class, 2L);
        UserEntity admin = UserEntity.builder().firstName("Admin").lastName("Adminov").email("admin@mail.com").password("123").address("avenue").phone("+admin123").username("admin").role(adminrole).build();
        UserEntity user = UserEntity.builder().firstName("John").lastName("Doe").email("jdoe@mail.com").password("123").address("avenue").phone("+3169420").username("jdoe").role(userrole).build();
        UserEntity user2 = UserEntity.builder().firstName("Michael").lastName("Jordan").email("mjoe@mail.com").password("123").address("avenue").phone("+3169420").username("jrdn").role(userrole).build();
        entityManager.persist(admin);
        entityManager.persist(user);
        entityManager.persist(user2);
        System.out.println("Users inserted");
    }
    protected void InsertTestAuctions(){
        InsertTestUsers();
        UserEntity creator = entityManager.find(UserEntity.class, 2L);
        Instant aucStart = TimeHelper.Now().plus(1, ChronoUnit.DAYS);
        Instant aucEnd = TimeHelper.Now().plus(4, ChronoUnit.DAYS);
        AuctionEntity auction = AuctionEntity.builder().creator(creator).carBrand("BMW").carModel("330i").carDesc("desc").carYear(2002).startingPrice(1000).buyoutPrice(2000).mileage(10000).hasSold(false).location("123 avenue").startsOn(aucStart).endsOn(aucEnd).build();
        entityManager.persist(auction);

        ImageEntity img1 = ImageEntity.builder().imgUrl("img1").auction(auction).build();
        entityManager.persist(img1);

        System.out.println("Auctions inserted");
    }
    protected void CleanDB(){
        entityManager.createQuery("DELETE from ImageEntity").executeUpdate();
        entityManager.createQuery("DELETE from CommentEntity").executeUpdate();
        entityManager.createQuery("DELETE from BidEntity").executeUpdate();
        entityManager.createQuery("DELETE from AuctionEntity").executeUpdate();
        entityManager.createQuery("DELETE from UserEntity").executeUpdate();
        entityManager.createQuery("DELETE from RoleEntity").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE s3carspace_role AUTO_INCREMENT = 1").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE s3carspace_user AUTO_INCREMENT = 1").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE s3carspace_auction AUTO_INCREMENT = 1").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE s3carspace_auction_image AUTO_INCREMENT = 1").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE s3carspace_auction_comment AUTO_INCREMENT = 1").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE s3carspace_auction_bid AUTO_INCREMENT = 1").executeUpdate();
        System.out.println("DB wiped");
    }
}
