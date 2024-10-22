package com.example.groupproject.service;

import com.example.groupproject.dto.AdminDTO;
import com.example.groupproject.model.User;
import com.example.groupproject.model.Role;
import com.example.groupproject.model.Admin;
import com.example.groupproject.repository.AdminRepository;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class AdminService {
    private final AdminRepository adminRepo;
    @Lazy
    private final UserService userService;

    @Autowired
    public AdminService(AdminRepository adminRepo, UserService userService) {
        this.adminRepo = adminRepo;
        this.userService = userService;
    }
    /**
     * Saves a new {@link Admin} based on the provided {@link AdminDTO}.
     *
     * @param adminDTO the data transfer object representing the new admin.
     */
    public Admin saveAdmin(AdminDTO adminDTO) {
        if (adminDTO == null) {
            throw new IllegalArgumentException("AdminDTO cannot be null");
        }

        if (adminDTO.getUserId() <= 0) {
            throw new IllegalArgumentException("Invalid user ID: " + adminDTO.getUserId());
        }

        User user = userService.findUserById(adminDTO.getUserId());
        validateRoleIsAdmin(user);

        Admin adminToSave = new Admin();
        adminToSave.setUser(user);
        return adminRepo.save(adminToSave);
    }

    /**
     * Updates an existing {@link Admin} identified by its ID.
     *
     * @param adminId      the ID of the admin to update.
     * @param updatedAdmin the updated data transfer object for the admin.
     */
    public Admin updateAdminById(long adminId,  AdminDTO updatedAdmin) {
        User newUser = userService.findUserById(updatedAdmin.getUserId());
        validateRoleIsAdmin(newUser);

        Admin adminToUpdate = findAdminById(adminId);

        adminToUpdate.setUser(newUser);
        return adminRepo.save(adminToUpdate);
    }

    /**
     * Validates that the account role is "admin".
     *
     * @param user the account to validate.
     * @throws IllegalArgumentException if the account role is not "admin".
     */
    private void validateRoleIsAdmin(User user) {
        // Iterate over the authorities (roles) and check if the user has the "ROLE_ADMIN"
        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            throw new IllegalArgumentException("Account role must be 'admin' to assign account to an admin.");
        }


    }


    /**
     * Deletes an existing {@link Admin} identified by its ID.
     *
     * @param id the ID of the admin to delete.
     * @throws EntityNotFoundException if the admin with the specified ID is not found.
     */
    public void deleteAdminById(long id) {
        if (!doesAdminExist(id)){
            throw new EntityNotFoundException("Admin not found with id: " + id);
        }
        adminRepo.deleteById(id);
    }

    /**
     * Retrieves an existing {@link Admin} identified by its ID.
     *
     * @param id the ID of the admin to retrieve.
     * @return the admin with the specified ID.
     * @throws EntityNotFoundException if the admin with the specified ID is not found.
     */
    public  Admin findAdminById(long id) {
        return adminRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Admin not found with id: " + id));
    }

    /**
     * Retrieves all existing {@link Admin} entities.
     *
     * @return a list of all admins.
     */
    public List<Admin> findAllAdmins() {
        return adminRepo.findAll();
    }

    /**
     * Checks if a admin with the specified ID exists.
     *
     * @param id the ID of the admin to check.
     * @return {@code true} if the admin exists, {@code false} otherwise.
     */
    public boolean doesAdminExist(long id) {
        return adminRepo.existsById(id);
    }

}
