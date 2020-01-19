package mk.metalkat.webapi.controller.authentication;

import lombok.RequiredArgsConstructor;
import mk.metalkat.webapi.models.authentication.User;
import mk.metalkat.webapi.service.authentication.SecurityService;
import mk.metalkat.webapi.service.authentication.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/users")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class UserController {

    private final UserService userService;

    private final SecurityService securityService;

    @PostMapping("/registration")
    public User registerUser(@RequestBody User user) {
        User savedUser = userService.save(user);

        securityService.autoLogin(savedUser.getUsername(), savedUser.getPasswordConfirm());

        return savedUser;
    }







}
