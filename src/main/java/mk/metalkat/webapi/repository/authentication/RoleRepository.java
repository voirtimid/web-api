package mk.metalkat.webapi.repository.authentication;

import mk.metalkat.webapi.models.authentication.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
