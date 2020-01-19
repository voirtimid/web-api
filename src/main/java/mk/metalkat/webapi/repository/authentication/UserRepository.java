package mk.metalkat.webapi.repository.authentication;

import mk.metalkat.webapi.models.authentication.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
