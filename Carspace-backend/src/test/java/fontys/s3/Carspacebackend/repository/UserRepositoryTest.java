package fontys.s3.Carspacebackend.repository;

import fontys.s3.Carspacebackend.business.interfaces.IRoleRepository;
import fontys.s3.Carspacebackend.business.interfaces.IUserRepository;
import fontys.s3.Carspacebackend.converters.UserConverter;
import fontys.s3.Carspacebackend.domain.IRole;
import fontys.s3.Carspacebackend.domain.User;
import fontys.s3.Carspacebackend.persistence.Entity.RoleEntity;
import fontys.s3.Carspacebackend.persistence.Entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    private
    EntityManager entityManager;

    @Autowired private IUserRepository userRepo;
    @Autowired private IRoleRepository roleRepo;

    @Transactional
    @Test
    void save_shouldSaveUserWithAllFields() {
        //Create a new role to save in the db
        RoleEntity roleE = RoleEntity.builder().roleName("user").build();
        entityManager.persist(roleE);

        //Cannot create a business user object without the business role, so I need to get it
        IRole role = roleRepo.findById(roleE.getId());

        //Create a business object because the repository is tailored to my business layer and works with business objects
        User savedUser = User.builder().firstName("John").lastName("Doe").email("jdoe@mail.com").password("123").address("avenue").phone("+3169").username("test").role(role).build();
        Long userid = userRepo.saveUser(savedUser);
        assertNotNull(userid);
        //This is super bad but since there is no reference between the user object and entity, its id is not automatically updated
        //and i dont want to use the builder again and make a new user
        savedUser.setId(userid);

        //Find the user entity with the entity manager, but now I need to convert it
        UserEntity foundUserE = entityManager.find(UserEntity.class, userid);
        User foundUser = UserConverter.convertToPOJO(foundUserE);

        //The test passes but there are a lot of dependencies like the converters and the role repository, which should not be here
        assertEquals(savedUser, foundUser);
    }
}
