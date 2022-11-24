package fontys.s3.carspacebackend.controller;

import fontys.s3.carspacebackend.business.service.IUserService;
import fontys.s3.carspacebackend.configuration.security.isauthenticated.IsAuthenticated;
import fontys.s3.carspacebackend.controller.dto.UserDTO;
import fontys.s3.carspacebackend.controller.responses.GenericObjectResponse;
import fontys.s3.carspacebackend.converters.UserConverter;
import fontys.s3.carspacebackend.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final IUserService userService;

    @GetMapping("{username}")
    public ResponseEntity<GenericObjectResponse> getUserProfile(@PathVariable String username){
        User loggedUser = userService.getUserByUsername(username);
        UserDTO userDTO = UserConverter.convertToDTO(loggedUser);
        GenericObjectResponse res = GenericObjectResponse.builder().message("User fetched").obj(userDTO).build();
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}
