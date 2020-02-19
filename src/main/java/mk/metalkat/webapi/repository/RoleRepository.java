package mk.metalkat.webapi.repository;

import mk.metalkat.webapi.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleNameEquals(String roleName);
}
