package com.example.groupproject.service;

import com.example.groupproject.dto.HeadmasterDTO;
import com.example.groupproject.model.School;
import com.example.groupproject.model.User;
import com.example.groupproject.model.Role;
import com.example.groupproject.model.Headmaster;
import com.example.groupproject.repository.HeadmasterRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class HeadmasterService {
    private final HeadmasterRepository principalRepo;
    private final UserService userService;
    private final SchoolService schoolService;

    @Autowired
    public HeadmasterService(HeadmasterRepository principalRepo, UserService userService, SchoolService schoolService) {
        this.principalRepo = principalRepo;
        this.userService = userService;
        this.schoolService = schoolService;
    }
    /**
     * Saves a new {@link Headmaster} based on the provided {@link HeadmasterDTO}.
     *
     * @param headmasterDTO the data transfer object representing the new principal.
     */
    public Headmaster savePrincipal(HeadmasterDTO headmasterDTO) {
        User user = userService.findUserById(headmasterDTO.getUserId());
        validateRoleIsHeadmaster(user);

        // Fetch the school by ID from the DTO
        School school = schoolService.findSchoolById(headmasterDTO.getSchoolId());
        if (school == null) {
            throw new EntityNotFoundException("School not found with ID: " + headmasterDTO.getSchoolId());
        }

        Headmaster headmasterToSave = new Headmaster();
        headmasterToSave.setUser(user);
        headmasterToSave.setSchool(school); // Assuming Headmaster has a setSchool method

        return principalRepo.save(headmasterToSave);
    }



    public void validateRoleIsHeadmaster(User user) {
        boolean isHeadmaster = user.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_HEADMASTER"));
        if (!isHeadmaster) {
            throw new IllegalArgumentException("Role must be headmaster to assign account to a student");
        }
    }



    /**
     * Updates an existing {@link Headmaster} identified by its ID.
     *
     * @param principalId      the ID of the principal to update.
     * @param updatedPrincipal the updated data transfer object for the principal.
     */
    public Headmaster updatePrincipalById(long principalId, HeadmasterDTO updatedPrincipal) {
        if (updatedPrincipal == null) {
            throw new IllegalArgumentException("Updated Principal cannot be null");
        }

        // Validate user ID
        if (updatedPrincipal.getUserId() <= 0) {
            throw new IllegalArgumentException("Invalid user ID: " + updatedPrincipal.getUserId());
        }

        // Fetch user by userId
        User newUser = userService.findUserById(updatedPrincipal.getUserId());
        validateRoleIsHeadmaster(newUser);

        Headmaster headmasterToUpdate = findPrincipalById(principalId);
        if (headmasterToUpdate == null) {
            throw new EntityNotFoundException("Headmaster not found with ID: " + principalId);
        }

        headmasterToUpdate.setUser(newUser);
        return principalRepo.save(headmasterToUpdate);
    }


    /**
     * Validates that the account role is "principal".
     *
     * @param user the account to validate.
     * @throws IllegalArgumentException if the account role is not "principal".
     */



    /**
     * Deletes an existing {@link Headmaster} identified by its ID.
     *
     * @param id the ID of the principal to delete.
     * @throws EntityNotFoundException if the principal with the specified ID is not found.
     */
    public void deletePrincipalById(long id) {
        if (!doesPrincipalExist(id)){
            throw new EntityNotFoundException("Principal not found with id: " + id);
        }
        principalRepo.deleteById(id);
    }

    /**
     * Retrieves an existing {@link Headmaster} identified by its ID.
     *
     * @param id the ID of the principal to retrieve.
     * @return the principal with the specified ID.
     * @throws EntityNotFoundException if the principal with the specified ID is not found.
     */
    public Headmaster findPrincipalById(long id) {
        return principalRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Principal not found with id: " + id));
    }

    /**
     * Retrieves all existing {@link Headmaster} entities.
     *
     * @return a list of all principals.
     */
    public List<Headmaster> findAllPrincipals() {
        return principalRepo.findAll();
    }

    /**
     * Checks if a principal with the specified ID exists.
     *
     * @param id the ID of the principal to check.
     * @return {@code true} if the principal exists, {@code false} otherwise.
     */
    public boolean doesPrincipalExist(long id) {
        return principalRepo.existsById(id);
    }

}
