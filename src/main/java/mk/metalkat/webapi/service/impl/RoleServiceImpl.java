package mk.metalkat.webapi.service.impl;

import lombok.RequiredArgsConstructor;
import mk.metalkat.webapi.exceptions.ModelNotFoundException;
import mk.metalkat.webapi.models.Role;
import mk.metalkat.webapi.repository.RoleRepository;
import mk.metalkat.webapi.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role getRoleById(Long roleId) {
        return roleRepository.findById(roleId).orElseThrow(() -> new ModelNotFoundException("Role does not exist!"));
    }

    @Override
    public Role getRoleByName(String roleName) {
        return roleRepository.findByRoleNameEquals(roleName);
    }

    @Override
    public Role updateRole(Long roleId, Role modifiedRole) {
        Optional<Role> optionalRole = roleRepository.findById(roleId);
        if (optionalRole.isPresent()) {
            return roleRepository.save(modifiedRole);
        }
        throw new ModelNotFoundException("Role does not exist!");
    }

    @Override
    public Role deleteRoleByName(String roleName) {
        Role roleToDelete = roleRepository.findByRoleNameEquals(roleName);
        if (roleToDelete == null) {
            throw new ModelNotFoundException("Role does not exist");
        }
        roleRepository.delete(roleToDelete);
        return roleToDelete;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
