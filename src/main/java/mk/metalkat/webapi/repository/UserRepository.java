package mk.metalkat.webapi.repository;

import mk.metalkat.webapi.models.jpa.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmailEquals(String email);
}
