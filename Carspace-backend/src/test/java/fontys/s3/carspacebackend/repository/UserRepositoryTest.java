package fontys.s3.carspacebackend.repository;

import fontys.s3.carspacebackend.domain.IRole;
import fontys.s3.carspacebackend.domain.User;
import fontys.s3.carspacebackend.persistence.repository.impl.RoleRepository;
import fontys.s3.carspacebackend.persistence.repository.impl.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest extends RepositoryTest{

    @Autowired private UserRepository userRepo;
    @Autowired private RoleRepository roleRepo;

    @AfterEach
    void tearDown()
    {
        CleanDB();
    }
    @Transactional
    @Test
    void save_shouldGetRoleAndSaveUserWithAllFields() {
        InsertTestRoles();
        IRole role = roleRepo.findById(1L);

        User savedUser = User.builder().firstName("John").lastName("Doe").email("jdoe@mail.com").password("123").address("avenue").phone("+3169").username("test").role(role).build();
        Long userid = userRepo.saveUser(savedUser);
        assertNotNull(userid);

        User foundUser = userRepo.findById(userid);

        assertEquals(userid, foundUser.getId());
        assertEquals(savedUser.getFirstName(), foundUser.getFirstName());
        assertEquals(savedUser.getLastName(), foundUser.getLastName());
        assertEquals(savedUser.getUsername(), foundUser.getUsername());
        assertEquals(savedUser.getPassword(), foundUser.getPassword());
        assertEquals(savedUser.getAddress(), foundUser.getAddress());
        assertEquals(savedUser.getEmail(), foundUser.getEmail());
        assertEquals(savedUser.getPhone(), foundUser.getPhone());
        assertEquals(savedUser.getRole().getRoleId(), foundUser.getRole().getRoleId());
    }

    @Transactional
    @Test
    void save_shouldSaveUserAndGetByUsername() {
        InsertTestRoles();
        IRole role = roleRepo.findById(1L);

        User savedUser = User.builder().firstName("John").lastName("Doe").email("jdoe@mail.com").password("123").address("avenue").phone("+3169").username("test").role(role).build();
        Long userid = userRepo.saveUser(savedUser);
        assertNotNull(userid);

        User foundUser = userRepo.getUserByUsername("test");

        assertEquals(userid, foundUser.getId());
        assertEquals(savedUser.getFirstName(), foundUser.getFirstName());
        assertEquals(savedUser.getLastName(), foundUser.getLastName());
        assertEquals(savedUser.getUsername(), foundUser.getUsername());
        assertEquals(savedUser.getPassword(), foundUser.getPassword());
        assertEquals(savedUser.getAddress(), foundUser.getAddress());
        assertEquals(savedUser.getEmail(), foundUser.getEmail());
        assertEquals(savedUser.getPhone(), foundUser.getPhone());
        assertEquals(savedUser.getRole().getRoleId(), foundUser.getRole().getRoleId());
    }
}
