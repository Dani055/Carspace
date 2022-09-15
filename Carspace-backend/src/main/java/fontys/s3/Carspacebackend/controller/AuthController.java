package fontys.s3.Carspacebackend.controller;

import fontys.s3.Carspacebackend.business.service.IUserService;
import fontys.s3.Carspacebackend.business.service.impl.UserConverter;
import fontys.s3.Carspacebackend.domain.User;
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
    public ResponseEntity<GenericObjectResponse> signUp(@RequestBody @Valid UserEntity user){
        UserEntity createdUser = userService.saveUser(user);
        GenericObjectResponse res = GenericObjectResponse.builder().message("Register successful").obj(createdUser).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PostMapping("signin")
    public ResponseEntity<GenericObjectResponse> signIn(@RequestBody @Valid LoginReq req){
        User loggedUser = UserConverter.convert(userService.findUserByUsername(req));
        GenericObjectResponse res = GenericObjectResponse.builder().message("Login successful").obj(loggedUser).build();
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}
