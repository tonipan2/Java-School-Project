package com.example.groupproject.service;

import com.example.groupproject.dto.StudentDTO;
import com.example.groupproject.model.*;
import com.example.groupproject.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;

@Service

public class StudentService {
    private final StudentRepository studentRepo;
    @Autowired
    private final UserService userService;
    @Autowired
    @Lazy
    private ParentService parentService;
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private KlassService klassService;

    @Autowired
    public StudentService(StudentRepository studentRepo, UserService userService) {
        this.studentRepo = studentRepo;
        this.userService = userService;
    }
    /**
     * Saves a new {@link Student} based on the provided {@link StudentDTO}.
     *
     * @param studentDTO the data transfer object representing the new student.
     */
    public Student saveStudent(StudentDTO studentDTO) {
        User user = userService.findUserById(studentDTO.getUserId()); // Corrected to use userId
        validateRoleIsStudent(user);

        Student studentToSave = new Student();
        studentToSave.setUser(user);

        School school = schoolService.findSchoolById(studentDTO.getSchoolId());
        studentToSave.setSchool(school);

        Klass klass = klassService.findKlassById(studentDTO.getKlassId());
        studentToSave.setKlass(klass);

        if (studentDTO.getParentIds() != null && !studentDTO.getParentIds().isEmpty()) {
            Set<Parent> parents = parentService.findParentsByIds(studentDTO.getParentIds());
            studentToSave.setParents(parents);
        }

        return studentRepo.save(studentToSave);
    }
    /**
     * Updates an existing {@link Student} identified by its ID.
     *
     * @param studentId      the ID of the student to update.
     * @param updatedStudent the updated data transfer object for the student.
     */
    public Student updateStudentById(long studentId, StudentDTO updatedStudent) {
        // Ensure the user ID is valid
        if (updatedStudent.getUserId() <= 0) {
            throw new IllegalArgumentException("Invalid user ID: " + updatedStudent.getUserId());
        }

        // Find the User entity
        User newUser = userService.findUserById(updatedStudent.getUserId());
        validateRoleIsStudent(newUser);

        // Fetch the Student entity and handle the Optional
        Student studentToUpdate = findStudentById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + studentId));

        // Only update the user if it's different from the current one
        User currentUser = studentToUpdate.getUser();
        if (currentUser == null || currentUser.getId() != newUser.getId()) {
            studentToUpdate.setUser(newUser);
        }

        // Update other fields if necessary
        studentToUpdate.setKlass(klassService.findKlassById(updatedStudent.getKlassId()));
        studentToUpdate.setSchool(schoolService.findSchoolById(updatedStudent.getSchoolId()));

        return studentRepo.save(studentToUpdate);
    }


    /**
     * Validates that the account role is "student".
     *
     * @param user the account to validate.
     * @throws IllegalArgumentException if the account role is not "student".
     */
    public void validateRoleIsStudent(User user) {
        boolean isStudent = user.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_STUDENT"));
        if (!isStudent) {
            throw new IllegalArgumentException("Role must be student to assign account to a student");
        }
    }


    /**
     * Deletes an existing {@link Student} identified by its ID.
     *
     * @param id the ID of the student to delete.
     * @throws EntityNotFoundException if the student with the specified ID is not found.
     */
    public void deleteStudentById(long id) {
        if (!doesStudentExist(id)){
            throw new EntityNotFoundException("Student not found with id: " + id);
        }
        studentRepo.deleteById(id);
    }

    /**
     * Retrieves an existing {@link Student} identified by its ID.
     *
     * @param id the ID of the student to retrieve.
     * @return the student with the specified ID.
     * @throws EntityNotFoundException if the student with the specified ID is not found.
     */
    public Optional<Student> findStudentById(long id) {
        return studentRepo.findById(id);
    }

    /**
     * Retrieves all existing {@link Student} entities.
     *
     * @return a list of all students.
     */
    public List<Student> findAllStudents() {
        return studentRepo.findAll();
    }

    /**
     * Checks if a student with the specified ID exists.
     *
     * @param id the ID of the student to check.
     * @return {@code true} if the student exists, {@code false} otherwise.
     */
    public boolean doesStudentExist(long id) {
        return studentRepo.existsById(id);
    }

    /**
     * Retrieves all grades for a specific student by their ID.
     *
     * @param studentId the ID of the student whose grades are to be retrieved.
     * @return a list of grades for the student.
     * @throws EntityNotFoundException if the student with the specified ID is not found.
     */
    public List<Grade> getStudentGrades(long studentId) {

        Student student = findStudentById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + studentId));

        return new ArrayList<>(student.getGrades());
    }

    /**
     * Retrieves all absences for a specific student by their ID.
     *
     * @param studentId the ID of the student whose grades are to be retrieved.
     * @return a list of absences for the student.
     * @throws EntityNotFoundException if the student with the specified ID is not found.
     */
    public List<Absence> getStudentAbsences(long studentId){
        Student student = findStudentById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + studentId));
        return new ArrayList<>(student.getAbsences());
    }
}
