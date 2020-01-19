package mk.metalkat.webapi.service.authentication.impl;

import lombok.RequiredArgsConstructor;
import mk.metalkat.webapi.models.authentication.Role;
import mk.metalkat.webapi.models.authentication.User;
import mk.metalkat.webapi.repository.authentication.RoleRepository;
import mk.metalkat.webapi.repository.authentication.UserRepository;
import mk.metalkat.webapi.service.authentication.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean addRoleToUser(String username, Long roleId) {
        User user = userRepository.findByUsername(username);
        Optional<Role> optionalRole = roleRepository.findById(roleId);
        if (user != null && optionalRole.isPresent()) {
            user.addRole(optionalRole.get());
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
