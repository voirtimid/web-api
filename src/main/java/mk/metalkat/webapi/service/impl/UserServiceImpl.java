package mk.metalkat.webapi.service.impl;

import lombok.RequiredArgsConstructor;
import mk.metalkat.webapi.exceptions.ModelNotFoundException;
import mk.metalkat.webapi.models.User;
import mk.metalkat.webapi.models.dto.UserDTO;
import mk.metalkat.webapi.repository.UserRepository;
import mk.metalkat.webapi.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

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
    public User createNewUser(UserDTO userDTO) {
        User user = userDTO.getUser();
        User optionalUser = userRepository.findByEmailEquals(user.getEmail());
        if (optionalUser != null) {
            throw new ModelNotFoundException("User with this username already exist!");
        }

        return userRepository.save(user);
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
