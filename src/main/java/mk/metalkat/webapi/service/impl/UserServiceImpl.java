package mk.metalkat.webapi.service.impl;

import lombok.RequiredArgsConstructor;
import mk.metalkat.webapi.exceptions.ModelNotFoundException;
import mk.metalkat.webapi.exceptions.UserNotValidException;
import mk.metalkat.webapi.models.dto.ValidatedUserDTO;
import mk.metalkat.webapi.models.jpa.User;
import mk.metalkat.webapi.models.dto.UserDTO;
import mk.metalkat.webapi.repository.UserRepository;
import mk.metalkat.webapi.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public ValidatedUserDTO createNewUser(UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        User optionalUser = userRepository.findByEmailEquals(user.getEmail());
        if (optionalUser != null) {
            throw new ModelNotFoundException("User with this username already exist!");
        }

        User createdUser = userRepository.saveAndFlush(user);
        return new ValidatedUserDTO(createdUser.getUserId(), createdUser.getEmail(), createdUser.getRole(), UUID.randomUUID());
    }

    @Override
    public ValidatedUserDTO validateUser(User user) {
        if (user.getEmail() == null) {
            throw new ModelNotFoundException("User don't have email in the request!");
        } else {
            User userToValidate = userRepository.findByEmailEquals(user.getEmail());
            if (userToValidate != null && userToValidate.getPassword().equals(user.getPassword())) {
                return new ValidatedUserDTO(userToValidate.getUserId(), userToValidate.getEmail(), userToValidate.getRole(), UUID.randomUUID());
            }
        }
        throw new UserNotValidException("The provided credentials are wrong!");
    }

    @Override
    public User getUserByEmail(String username) {
        return userRepository.findByEmailEquals(username);
    }

    @Override
    public User updateUser(String username, User updatedUser) {
        User user = userRepository.findByEmailEquals(username);
        if (user == null) {
            throw new ModelNotFoundException("User does not exist!");
        }
        return userRepository.save(updatedUser);
    }

    @Override
    public User deleteUser(String username) {
        User user = userRepository.findByEmailEquals(username);
        if (user == null) {
            throw new ModelNotFoundException("User does not exist!");
        }
        userRepository.delete(user);
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
