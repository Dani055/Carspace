package fontys.s3.Carspacebackend.controller;

import fontys.s3.Carspacebackend.business.service.IUserService;
import fontys.s3.Carspacebackend.converters.UserConverter;
import fontys.s3.Carspacebackend.domain.User;
import fontys.s3.Carspacebackend.domain.dto.UserDTO;
import fontys.s3.Carspacebackend.domain.impl.UserRole;
import fontys.s3.Carspacebackend.domain.requests.CreateUserReq;
import fontys.s3.Carspacebackend.domain.requests.LoginReq;
import fontys.s3.Carspacebackend.domain.responses.GenericObjectResponse;
import fontys.s3.Carspacebackend.domain.responses.ResourceCreatedResponse;
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
    public ResponseEntity<ResourceCreatedResponse> signUp(@RequestBody @Valid CreateUserReq req){
        User toCreate = User.builder().firstName(req.getFirstName()).lastName(req.getLastName()).email(req.getEmail()).username(req.getUsername()).password(req.getPassword()).address(req.getAddress()).phone(req.getPhone()).build();
        Long userId = userService.registerUser(toCreate);
        ResourceCreatedResponse res = ResourceCreatedResponse.builder().message("Register successful").id(userId).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PostMapping("signin")
    public ResponseEntity<GenericObjectResponse> signIn(@RequestBody @Valid LoginReq req){
        User loggedUser = userService.loginUser(req.getUsername(), req.getPassword());
        UserDTO userDTO = UserConverter.convertToDTO(loggedUser);
        GenericObjectResponse res = GenericObjectResponse.builder().message("Login successful").obj(userDTO).build();
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}
