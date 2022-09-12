package fontys.s3.Carspacebackend.controller;

import fontys.s3.Carspacebackend.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    @GetMapping
    public ResponseEntity<User> signIn(){
        User u = User.builder().id(new Long(1)).firstName("Test").lastName("tester").build();
        return ResponseEntity.ok(u);
    }
}
