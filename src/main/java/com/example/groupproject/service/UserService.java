package com.example.groupproject.service;

import com.example.groupproject.dto.UserDTO;
import com.example.groupproject.model.User;
import com.example.groupproject.model.Role;
import com.example.groupproject.repository.UserRepository;
import com.example.groupproject.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service @Transactional @Slf4j
public class UserService {

    @Lazy
    private final UserRepository userRepo;
    @Lazy
    private final RoleRepository userRoleRepo;

    public UserService(UserRepository userRepo, RoleRepository userRoleRepo) {
        this.userRepo = userRepo;
        this.userRoleRepo = userRoleRepo;
    }

    @Transactional
    public User saveUser(UserDTO userDTO) {
        log.info("Saving new user {} to the database", userDTO.getUsername());

        // Check if email or username already exists
        checkIfEmailExists(userDTO.getEmail());
        checkIfUsernameExists(userDTO.getUsername());

        // Create new User object
        User userToSave = new User();
        userToSave.setUsername(userDTO.getUsername());

        // Use BCryptPasswordEncoder directly
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encryptedPassword = encoder.encode(userDTO.getPassword());
        userToSave.setPassword(encryptedPassword);

        // Set other fields
        userToSave.setFirstName(userDTO.getFirstName());
        userToSave.setLastName(userDTO.getLastName());
        userToSave.setEgn(userDTO.getEgn());
        userToSave.setAddress(userDTO.getAddress());
        userToSave.setBirthDate(userDTO.getBirthDate());
        userToSave.setPhoneNumber(userDTO.getPhoneNumber());
        userToSave.setEmail(userDTO.getEmail());

        userToSave.setAccountNonExpired(true);
        userToSave.setAccountNonLocked(true);
        userToSave.setCredentialsNonExpired(true);
        userToSave.setEnabled(true);

        // Set roles to the user
        if (userDTO.getAuthorities() != null && !userDTO.getAuthorities().isEmpty()) {
            Set<Role> authorities = userDTO.getAuthorities().stream()
                    .map(roleDTO -> {
                        Role role = userRoleRepo.findByAuthority(roleDTO.getAuthority())
                                .orElseThrow(() -> new IllegalArgumentException("Invalid Role: " + roleDTO.getAuthority()));
                        return role;
                    })
                    .collect(Collectors.toSet());

            userToSave.setAuthorities(authorities);
        }

        return userRepo.save(userToSave);
    }


    /**
     * Updates an existing {@link User} identified by its ID.
     *
     * @param userId      the ID of the user to update.
     * @param updatedUser the updated data transfer object for the user.
     */
    @Transactional
    public User updateUserById(long userId, UserDTO updatedUser) {
        log.info("Updating user {} in the database", userId);

        // Find the user by ID (from the path variable, not from updatedUser)
        User userToUpdate = findUserById(userId);
        if (userToUpdate == null) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }

        // Update user details
        userToUpdate.setUsername(updatedUser.getUsername());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(updatedUser.getPassword(), userToUpdate.getPassword())) {
            String encryptedPassword = encoder.encode(updatedUser.getPassword());
            userToUpdate.setPassword(encryptedPassword);
        }

        userToUpdate.setEmail(updatedUser.getEmail());
        userToUpdate.setFirstName(updatedUser.getFirstName());
        userToUpdate.setLastName(updatedUser.getLastName());
        userToUpdate.setAddress(updatedUser.getAddress());
        userToUpdate.setEgn(updatedUser.getEgn());
        userToUpdate.setBirthDate(updatedUser.getBirthDate());
        userToUpdate.setPhoneNumber(updatedUser.getPhoneNumber());

        // Update user roles
        Set<Role> newAuthorities = new HashSet<>();
        if (updatedUser.getAuthorities() != null && !updatedUser.getAuthorities().isEmpty()) {
            for (Role userRole : updatedUser.getAuthorities()) {
                Role role = RoleService.findRoleById(userRole.getId());
                newAuthorities.add(role);
            }
        }
        userToUpdate.setAuthorities(newAuthorities);

        // Save the updated user
        return userRepo.save(userToUpdate);
    }




    /**
     * Checks if an user with the specified email already exists.
     *
     * @param email the email to check.
     * @throws DataIntegrityViolationException if an user with the specified email already exists.
     */
    private void checkIfEmailExists(String email) {
        log.info("Checking if an user with email {} exists", email);
        if (userRepo.findByEmail(email).isPresent()) {
            throw new DataIntegrityViolationException("Email already exists");
        }
    }

    /**
     * Checks if an user with the specified username already exists.
     *
     * @param username the username to check.
     * @throws DataIntegrityViolationException if an user with the specified username already exists.
     */
    private void checkIfUsernameExists(String username) {
        log.info("Checking if an user with username {} exists", username);
        if (userRepo.findByUsername(username).isPresent()) {
            throw new DataIntegrityViolationException("Username already exists");
        }
    }
    public void updateUserUsername(long userId, UserDTO updatedUser) {
        log.info("Updating user {} username", userId);
        User userToUpdate =  findUserById(userId);
        String newUsername = updatedUser.getUsername();
        userToUpdate.setUsername(newUsername);
        userRepo.save(userToUpdate);
    }

    public void updateUserPassword(long userId, UserDTO updatedUser) {
        log.info("Updating user {} password", userId);
        User userToUpdate =  findUserById(userId);
        String newPassword = updatedUser.getPassword();
        userToUpdate.setPassword(newPassword);
        userRepo.save(userToUpdate);
    }

    public void updateUserEmail(long userId, UserDTO updatedUser) {
        log.info("Updating user {}", userId);
        User userToUpdate =  findUserById(userId);
        String newEmail = updatedUser.getEmail();
        userToUpdate.setEmail(newEmail);
        userRepo.save(userToUpdate);
    }

    /**
     * Deletes an existing {@link User} identified by its ID.
     *
     * @param id the ID of the user to delete.
     * @throws EntityNotFoundException if the user with the specified ID is not found.
     */
    public void deleteUserById(long id) {
        log.info("Deleting user {}", id);
        if (!doesUserExist(id)){
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        userRepo.deleteById(id);
    }

    /**
     * Retrieves an existing {@link User} identified by its ID.
     *
     * @param id the ID of the user to retrieve.
     * @return the user with the specified ID.
     * @throws EntityNotFoundException if the user with the specified ID is not found.
     */
    public User findUserById(long id) {
        log.info("Fetching an user by id {}", id);
        return  userRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    /**
     * Retrieves all existing {@link User} entities.
     *
     * @return a list of all users.
     */
    public List<User> findAllUsers() {
        log.info("Fetching all users");
        return  userRepo.findAll();
    }


    /**
     * Finds an user by password and username.
     *
     * @param password The password of the user.
     * @param username The username of the user.
     * @return The found user.
     * @throws EntityNotFoundException if the user is not found with the given username and password.
     */
    public User findUserByPasswordAndUsername(String password, String username) {
        log.info("Fetching user by password {} and username {}", password, username);
        return userRepo.findUserByPasswordAndUsername(password, username).orElseThrow(() -> new EntityNotFoundException("User not found with username and password: " + username + " " + password));
    }

    public User findUserByUsername(String username) {
        log.info("Fetching user by username {}", username);
        return userRepo.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));
    }

    /**
     * Checks if an user with the specified ID exists.
     *
     * @param id the ID of the user to check.
     * @return {@code true} if the user exists, {@code false} otherwise.
     */
    public boolean doesUserExist(long id) {
        return userRepo.existsById(id);
    }


}
