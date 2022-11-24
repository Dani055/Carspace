package fontys.s3.carspacebackend.converter;

import fontys.s3.carspacebackend.controller.dto.CommentDTO;
import fontys.s3.carspacebackend.controller.dto.UserDTO;
import fontys.s3.carspacebackend.converters.CommentConverter;
import fontys.s3.carspacebackend.domain.Comment;
import fontys.s3.carspacebackend.domain.User;
import fontys.s3.carspacebackend.domain.impl.UserRole;
import fontys.s3.carspacebackend.persistence.Entity.CommentEntity;
import fontys.s3.carspacebackend.persistence.Entity.RoleEntity;
import fontys.s3.carspacebackend.persistence.Entity.UserEntity;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

public class CommentConverterTest {

    @Test
    void testConvertToPOJO(){
        Instant now = Instant.now();
        RoleEntity roleE = RoleEntity.builder().id(99L).roleName("user").build();
        UserEntity userE = UserEntity.builder().id(1L).role(roleE).username("jdoe").password("pass").firstName("john").lastName("doe").email("jdoe@gmail.com").address("avenue 123").phone("+311").build();

        CommentEntity commentEntity = CommentEntity.builder().id(2L).text("a comment").createdOn(now).creator(userE).build();

        Comment c = CommentConverter.convertToPOJO(commentEntity);

        assertEquals(2L, c.getId());
        assertEquals("a comment", c.getText());
        assertEquals(now, c.getCreatedOn());
        assertTrue(c.getCreator() instanceof User);
    }

    @Test
    void testConvertToPOJO_null(){
        CommentEntity commentEntity = null;

        Comment c = CommentConverter.convertToPOJO(commentEntity);

        assertNull(c);
    }

    @Test
    void testConvertToEntity(){
        Instant now = Instant.now();

        UserRole role = UserRole.builder().id(99L).role("user").build();
        User user = User.builder().id(1L).role(role).username("jdoe").password("pass").firstName("john").lastName("doe").email("jdoe@gmail.com").address("avenue 123").phone("+311").build();

        Comment c = Comment.builder().id(2L).text("a comment").createdOn(now).creator(user).build();

        CommentEntity commentE = CommentConverter.convertToEntity(c);

        assertEquals(2L, commentE.getId());
        assertEquals("a comment", commentE.getText());
        assertEquals(now, commentE.getCreatedOn());
        assertTrue(commentE.getCreator() instanceof UserEntity);
    }

    @Test
    void testConvertToEntity_null(){
        Comment c = null;

        CommentEntity commentE = CommentConverter.convertToEntity(c);

        assertNull(commentE);
    }

    @Test
    void testConvertToDTO(){
        Instant now = Instant.now();

        UserRole role = UserRole.builder().id(99L).role("user").build();
        User user = User.builder().id(1L).role(role).username("jdoe").password("pass").firstName("john").lastName("doe").email("jdoe@gmail.com").address("avenue 123").phone("+311").build();

        Comment c = Comment.builder().id(2L).text("a comment").createdOn(now).creator(user).build();

        CommentDTO dto = CommentConverter.convertToDTO(c);

        assertEquals(2L, dto.getId());
        assertEquals("a comment", dto.getText());
        assertEquals(now, dto.getCreatedOn());
        assertTrue(dto.getCreator() instanceof UserDTO);
    }

    @Test
    void testConvertToDTO_null(){
        Comment c = null;

        CommentDTO dto = CommentConverter.convertToDTO(c);

        assertNull(dto);
    }
}
