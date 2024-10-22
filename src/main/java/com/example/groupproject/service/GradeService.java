package com.example.groupproject.service;

import com.example.groupproject.dto.GradeDTO;
import com.example.groupproject.model.Grade;
import com.example.groupproject.model.Student;
import com.example.groupproject.model.Subject;
import com.example.groupproject.model.Term;
import com.example.groupproject.repository.GradeRepository;
import com.example.groupproject.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service

public class GradeService {
    private final GradeRepository gradeRepo;
    private final StudentRepository studentRepo;
    @Autowired
    private StudentService studentService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private TermService termService;

    @Autowired
    public GradeService(GradeRepository gradeRepo, StudentRepository studentRepo) {
        this.gradeRepo = gradeRepo;
        this.studentRepo = studentRepo;
    }

    /**
     * Saves a new {@link Grade} based on the provided {@link GradeDTO}.
     *
     * @param gradeDTO the data transfer object representing the new grade.
     */
    public Grade saveGrade(GradeDTO gradeDTO) {
        Grade gradeToSave = new Grade();

        // Set the grade
        gradeToSave.setGrade(gradeDTO.getGrade());

        // Set the dateAdded to the current time
        gradeToSave.setDateAdded(LocalDateTime.now());

        // Set the dateOfGrade
        gradeToSave.setDateOfGrade(gradeDTO.getDateOfGrade());

        // Fetch the student by ID
        Optional<Student> studentOptional = studentService.findStudentById(gradeDTO.getStudentId());
        Student student = studentOptional.orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + gradeDTO.getStudentId()));
        gradeToSave.setStudent(student);

        // Fetch the subject by ID
        Optional<Subject> subjectOptional = Optional.ofNullable(subjectService.findSubjectById(gradeDTO.getSubjectId()));
        Subject subject = subjectOptional.orElseThrow(() -> new EntityNotFoundException("Subject not found with id: " + gradeDTO.getSubjectId()));
        gradeToSave.setSubject(subject);

        // Set the gradeType
        gradeToSave.setGradeType(gradeDTO.getGradeType());

        Optional<Term> termOptional = Optional.ofNullable(termService.findTermById(gradeDTO.getTermId()));
        Term term = termOptional.orElseThrow(() -> new EntityNotFoundException("Term not found with id: " + gradeDTO.getTermId()));
        gradeToSave.setTerm(term);

        return gradeRepo.save(gradeToSave);
    }

    /**
     * Updates an existing {@link Grade} identified by its ID.
     *
     * @param gradeId      the ID of the grade to update.
     * @param updatedGrade the updated data transfer object for the grade.
     */
    public Grade updateGradeById(long gradeId, GradeDTO updatedGrade) {
        Grade gradeToUpdate = findGradeById(gradeId);

        if (updatedGrade.getGrade() != null) {
            gradeToUpdate.setGrade(updatedGrade.getGrade());
        }

        return gradeRepo.save(gradeToUpdate);
    }

    /**
     * Deletes an existing {@link Grade} identified by its ID.
     *
     * @param id the ID of the grade to delete.
     * @throws EntityNotFoundException if the grade with the specified ID is not found.
     */
    public void deleteGradeById(long id) {
        if (!doesGradeExist(id)){
            throw new EntityNotFoundException("Grade not found with id: " + id);
        }
        gradeRepo.deleteById(id);
    }

    /**
     * Retrieves an existing {@link Grade} identified by its ID.
     *
     * @param id the ID of the grade to retrieve.
     * @return the grade with the specified ID.
     * @throws EntityNotFoundException if the grade with the specified ID is not found.
     */
    public Grade findGradeById(long id) {
        return gradeRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Grade not found with id: " + id));
    }

    /**
     * Retrieves all existing {@link Grade} entities.
     *
     * @return a list of all grades.
     */
    public List<Grade> findAllGrades() {
        return gradeRepo.findAll();
    }

    /**
     * Checks if a grade with the specified ID exists.
     *
     * @param id the ID of the grade to check.
     * @return {@code true} if the grade exists, {@code false} otherwise.
     */

    public boolean doesGradeExist(long id) {
        return gradeRepo.existsById(id);
    }





}
