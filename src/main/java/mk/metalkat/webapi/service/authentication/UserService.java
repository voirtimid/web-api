package mk.metalkat.webapi.service.authentication;

import mk.metalkat.webapi.models.authentication.User;

public interface UserService {

    User save(User user);

    User findByUsername(String username);

    boolean addRoleToUser(String username, Long roleId);
}
