package mk.metalkat.webapi.service;

import mk.metalkat.webapi.models.jpa.User;
import mk.metalkat.webapi.models.dto.UserDTO;

import java.util.List;

public interface UserService {

    User getUserByEmail(String email);

    User updateUser(String username, User updatedUser);

    User createNewUser(UserDTO userDTO);

    User deleteUser(String username);

    List<User> getAllUsers();
}
