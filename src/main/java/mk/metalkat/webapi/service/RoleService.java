package mk.metalkat.webapi.service;

import mk.metalkat.webapi.models.jpa.Role;

import java.util.List;

public interface RoleService {

    Role getRoleById(Long roleId);

    Role getRoleByName(String roleName);

    Role updateRole(Long roleId, Role modifiedRole);

    Role deleteRoleByName(String roleName);

    List<Role> getAllRoles();
}
