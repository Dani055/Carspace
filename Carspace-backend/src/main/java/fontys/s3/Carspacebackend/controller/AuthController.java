package fontys.s3.Carspacebackend.controller;

import fontys.s3.Carspacebackend.business.service.IUserService;
import fontys.s3.Carspacebackend.converters.UserConverter;
import fontys.s3.Carspacebackend.domain.User;
import fontys.s3.Carspacebackend.domain.impl.UserRole;
import fontys.s3.Carspacebackend.domain.requests.CreateUserReq;
import fontys.s3.Carspacebackend.domain.requests.LoginReq;
import fontys.s3.Carspacebackend.domain.responses.GenericObjectResponse;
import fontys.s3.Carspacebackend.persistence.Entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private IUserService userService;

    @PostMapping("signup")
    public ResponseEntity<GenericObjectResponse> signUp(@RequestBody @Valid CreateUserReq req){
        User toCreate = User.builder().firstName(req.getFirstName()).lastName(req.getLastName()).email(req.getEmail()).username(req.getUsername()).password(req.getPassword()).address(req.getAddress()).build();
        User createdUser = userService.saveUser(toCreate);
        GenericObjectResponse res = GenericObjectResponse.builder().message("Register successful").obj(createdUser).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PostMapping("signin")
    public ResponseEntity<GenericObjectResponse> signIn(@RequestBody @Valid LoginReq req){
        User loggedUser = userService.findUserByUsername(req);
        GenericObjectResponse res = GenericObjectResponse.builder().message("Login successful").obj(loggedUser).build();
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}
