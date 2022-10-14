package fontys.s3.Carspacebackend.controller;

import fontys.s3.Carspacebackend.business.service.IUserService;
import fontys.s3.Carspacebackend.converters.UserConverter;
import fontys.s3.Carspacebackend.domain.User;
import fontys.s3.Carspacebackend.controller.dto.UserDTO;
import fontys.s3.Carspacebackend.controller.requests.CreateUserReq;
import fontys.s3.Carspacebackend.controller.requests.LoginReq;
import fontys.s3.Carspacebackend.controller.responses.GenericObjectResponse;
import fontys.s3.Carspacebackend.controller.responses.ResourceCreatedResponse;
import fontys.s3.Carspacebackend.exception.BadTokenException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
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

    @GetMapping("checkkey")
    public ResponseEntity<GenericObjectResponse> signIn(@RequestHeader HttpHeaders headers){
        //This would be where JWT is checked once I learn how to do it
        String token = headers.getFirst(HttpHeaders.AUTHORIZATION).split("Bearer ")[1];
        Long userId;
        try {
            userId = Long.parseLong(token);
        } catch (NumberFormatException e) {
            throw new BadTokenException();
        }
        User loggedUser = userService.getUserById(userId);
        UserDTO userDTO = UserConverter.convertToDTO(loggedUser);
        GenericObjectResponse res = GenericObjectResponse.builder().message("Auth key valid. Login successful").obj(userDTO).build();
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}
