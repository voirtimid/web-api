package mk.metalkat.webapi.controller;

import lombok.RequiredArgsConstructor;
import mk.metalkat.webapi.models.jpa.User;
import mk.metalkat.webapi.models.dto.UserDTO;
import mk.metalkat.webapi.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.43.211:3000"})
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{email}")
    public User getUserByEmail(@PathVariable("email") String email) {
        return userService.getUserByEmail(email);
    }

    @PostMapping
    public User createNewUser(@RequestBody UserDTO userDTO) {
        return userService.createNewUser(userDTO);
    }

    @PutMapping("/{email}")
    public User updateUser(@PathVariable("email") String email, @RequestBody User user) {
        return userService.updateUser(email, user);
    }

    @DeleteMapping("/{email}")
    public User deleteUser(@PathVariable("email") String email) {
        return userService.deleteUser(email);
    }
}
