package mk.metalkat.webapi.controller;

import lombok.RequiredArgsConstructor;
import mk.metalkat.webapi.models.jpa.Role;
import mk.metalkat.webapi.service.RoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/roles")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping(value = "/{roleName}")
    public Role findRoleByRoleName(@PathVariable("roleName") String roleName) {
        return roleService.getRoleByName(roleName);
    }

    @PutMapping(value = "/{roleId}")
    public Role updateRole(@PathVariable("roleId") Long roleId, @RequestBody Role role) {
        return roleService.updateRole(roleId, role);
    }

    @DeleteMapping(value = "/{roleName}")
    public Role deleteRoleByName(@PathVariable("roleName") String roleName) {
        return roleService.deleteRoleByName(roleName);
    }
}
