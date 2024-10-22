package com.example.groupproject.service;

import com.example.groupproject.dto.ParentDTO;
import com.example.groupproject.model.*;
import com.example.groupproject.repository.ParentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service

public class ParentService {
    private final ParentRepository parentRepo;
    @Autowired
    private final UserService userService;

    @Autowired
    @Lazy
    private StudentService studentService;

    @Autowired
    public ParentService(ParentRepository parentRepo, UserService userService) {
        this.parentRepo = parentRepo;
        this.userService = userService;
    }
    /**
     * Saves a new {@link Parent} based on the provided {@link ParentDTO}.
     *
     * @param parentDTO the data transfer object representing the new parent.
     */
    public Parent saveParent(ParentDTO parentDTO) {
        User user = userService.findUserById(parentDTO.getUserId());

        validateRoleIsParent(user);

        Parent parentToSave = new Parent();
        parentToSave.setUser(user);

        return parentRepo.save(parentToSave);
    }


    /**
     * Updates an existing {@link Parent} identified by its ID.
     *
     * @param parentId      the ID of the parent to update.
     * @param updatedParent the updated data transfer object for the parent.
     */
    public Parent updateParentById(long parentId, ParentDTO updatedParent) {
        // Validate user ID
        if (updatedParent.getUserId() <= 0) {
            throw new IllegalArgumentException("Invalid user ID: " + updatedParent.getUserId());
        }

        // Fetch user by user ID from updatedParent
        User newUser = userService.findUserById(updatedParent.getUserId());
        validateRoleIsParent(newUser);

        // Fetch the existing parent entity
        Parent parentToUpdate = findParentById(parentId);
        if (parentToUpdate == null) {
            throw new EntityNotFoundException("Parent not found with ID: " + parentId);
        }

        // Update parent information
        parentToUpdate.setUser(newUser);
        return parentRepo.save(parentToUpdate);
    }


    /**
     * Validates that the account role is "parent".
     *
     * @param user the account to validate.
     * @throws IllegalArgumentException if the account role is not "parent".
     */
    private void validateRoleIsParent(User user) {
        boolean isParent = user.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_PARENT"));
        if (!isParent) {
            throw new IllegalArgumentException("Role must be 'parent' to assign account to a parent");
        }
    }


    /**
     * Deletes an existing {@link Parent} identified by its ID.
     *
     * @param id the ID of the parent to delete.
     * @throws EntityNotFoundException if the parent with the specified ID is not found.
     */
    public void deleteParentById(long id) {
        if (!doesParentExist(id)){
            throw new EntityNotFoundException("Parent not found with id: " + id);
        }
        parentRepo.deleteById(id);
    }

    /**
     * Retrieves an existing {@link Parent} identified by its ID.
     *
     * @param id the ID of the parent to retrieve.
     * @return the parent with the specified ID.
     * @throws EntityNotFoundException if the parent with the specified ID is not found.
     */
    public  Parent findParentById(long id) {
        return parentRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Parent not found with id: " + id));
    }


    //DOPULNI OPISANIIEEEEE!!!!
    /**
     * Retrieves an existing {@link Parent} identified by its ID.
     *
     * //@param  the ID of the parent to retrieve.
     * @return the parent with the specified ID.
     * @throws EntityNotFoundException if the parent with the specified ID is not found.
     */

    public Set<Parent> findParentsByIds(Set<Long> parentIds) {
        return parentIds.stream()
                .map(this::findParentById)  // reuse the existing findParentById method
                .collect(Collectors.toSet());
    }
    /**
     * Retrieves all existing {@link Parent} entities.
     *
     * @return a list of all parents.
     */
    public List<Parent> findAllParents() {
        return parentRepo.findAll();
    }

    /**
     * Checks if a parent with the specified ID exists.
     *
     * @param id the ID of the parent to check.
     * @return {@code true} if the parent exists, {@code false} otherwise.
     */
    public boolean doesParentExist(long id) {
        return parentRepo.existsById(id);
    }

    /**
     * Retrieves all grades for the parent's students by the parent's ID.
     *
     * @param parentId is the ID of the parent whose students' grades are to be retrieved.
     * @return a map of grades for the student.
     */
    public Map<Student, Set<Grade>> getStudentGrades(long parentId) {

        Parent parent = findParentById(parentId);
        Set<Student> students = parent.getStudents();
        Map<Student, Set<Grade>> studentGradesMap = new HashMap<>();
        for (Student student : students) {
            Set<Grade> grades = new HashSet<>(studentService.getStudentGrades(student.getId()));
            studentGradesMap.put(student, grades);
        }
        return studentGradesMap;
    }

    /**
     * Retrieves all absences for the parent's students by the parent's ID.
     *
     * @param parentId is the ID of the parent whose students' grades are to be retrieved.
     * @return a map of grades for the student.
     */
    public Map<Student, Set<Absence>> getStudentAbsences(long parentId) {

        Parent parent = findParentById(parentId);
        Set<Student> students = parent.getStudents();
        Map<Student, Set<Absence>> studentAbsencesMap = new HashMap<>();
        for (Student student : students) {
            Set<Absence> absences = new HashSet<>(studentService.getStudentAbsences(student.getId()));
            studentAbsencesMap.put(student, absences);
        }
        return studentAbsencesMap;
    }

}
