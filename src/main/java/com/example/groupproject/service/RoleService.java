package com.example.groupproject.service;

import com.example.groupproject.dto.*;
import com.example.groupproject.model.*;
import com.example.groupproject.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private static RoleRepository roleRepo;

    @Autowired
    public RoleService(RoleRepository roleRepo) {
        this.roleRepo = roleRepo;
    }

    /**
     * Saves a new {@link Role} based on the provided {@link RoleDTO}.
     *
     * @param roleDTO the data transfer object representing the new account role.
     */
    public Role saveRole(RoleDTO roleDTO) {
        Role roleToSave = new Role();

        roleToSave.setAuthority(roleDTO.getAuthority());
        return roleRepo.save(roleToSave);
    }

    /**
     * Updates an existing {@link Role} identified by its ID.
     *
     * @param roleId the ID of the account role to update.
     * @param updatedRole   the updated data transfer object for the account role.
     */
    public Role updateRoleById(long roleId, RoleDTO updatedRole) {
        Role roleToUpdate =  findRoleById(roleId);

        String newRole = updatedRole.getAuthority();
        roleToUpdate.setAuthority(newRole);
        return roleRepo.save(roleToUpdate);
    }

    /**
     * Deletes an existing {@link Role} identified by its ID.
     *
     * @param id the ID of the account role to delete.
     * @throws EntityNotFoundException if the account role with the specified ID is not found.
     */
    public void deleteRoleById(long id) {
        if (!doesRoleExist(id)){
            throw new EntityNotFoundException("Role not found with id: " + id);
        }
        roleRepo.deleteById(id);
    }

    /**
     * Retrieves an existing {@link Role} identified by its ID.
     *
     * @param id the ID of the account role to retrieve.
     * @return the account role with the specified ID.
     * @throws EntityNotFoundException if the account role with the specified ID is not found.
     */
    public static Role findRoleById(long id) {
        return  roleRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + id));
    }

    /**
     * Retrieves all existing {@link Role} entities.
     *
     * @return a list of all account roles.
     */
    public List<Role> findAllRoles() {
        return roleRepo.findAll();
    }

    /**
     * Checks if an account role with the specified ID exists.
     *
     * @param id the ID of the account role to check.
     * @return {@code true} if the account role exists, {@code false} otherwise.
     */
    public boolean doesRoleExist(long id) {
        return roleRepo.existsById(id);
    }

}
