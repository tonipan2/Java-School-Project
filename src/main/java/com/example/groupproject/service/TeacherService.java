package com.example.groupproject.service;

import com.example.groupproject.dto.*;
import com.example.groupproject.model.*;
import com.example.groupproject.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service

public class TeacherService {
    private final TeacherRepository teacherRepo;
    private final UserService userService;
    private final SchoolRepository schoolRepo;
    private final SubjectRepository subjectRepo;
    private final AbsenceService absenceService;
    private final StudentService studentService;
    private final GradeService gradeService;
    private final SubjectService subjectService;
    private final TermService termService;

    @Autowired
    public TeacherService(TeacherRepository teacherRepo, UserService userService, SubjectRepository subjectRepo, SchoolRepository schoolRepo, AbsenceService absenceService, StudentService studentService, GradeService gradeService, SubjectService subjectService, TermService termService) {
        this.teacherRepo = teacherRepo;
        this.userService = userService;
        this.subjectRepo = subjectRepo;
        this.schoolRepo = schoolRepo;
        this.absenceService = absenceService;
        this.studentService = studentService;
        this.gradeService = gradeService;
        this.subjectService = subjectService;
        this.termService = termService;
    }
    /**
     * Saves a new {@link Teacher} based on the provided {@link TeacherDTO}.
     *
     * @param teacherDTO the data transfer object representing the new teacher.
     */

    public Teacher saveTeacher(TeacherDTO teacherDTO) {
        User user = userService.findUserById(teacherDTO.getUserId());
        validateRoleIsTeacher(user);

        Teacher teacherToSave = new Teacher();

        teacherToSave.setUser(user);

        Set<School> schools = new HashSet<>(schoolRepo.findAllById(teacherDTO.getSchoolIds()));
        teacherToSave.setSchools(schools);

        for (School school : schools) {
            school.getTeachers().add(teacherToSave);  // Update the inverse relationship
        }

        Set<Subject> subjects = new HashSet<>(subjectRepo.findAllById(teacherDTO.getSubjectIds()));
        teacherToSave.setSubjects(subjects);

        for (Subject subject : subjects) {
            subject.getTeachers().add(teacherToSave);  // Update the inverse relationship for subjects
        }
        return teacherRepo.save(teacherToSave);
    }




    /**
     * Updates an existing {@link Teacher} identified by its ID.
     *
     * @param teacherId      the ID of the teacher to update.
     * @param updatedTeacher the updated data transfer object for the teacher.
     */
    public Teacher updateTeacherById(long teacherId, TeacherDTO updatedTeacher) {
        // Find and validate the new user
        User newUser = userService.findUserById(updatedTeacher.getUserId());
        validateRoleIsTeacher(newUser);

        Teacher teacherToUpdate = findTeacherById(teacherId);
        teacherToUpdate.setUser(newUser);

        // Update schools (handle null or empty lists)
        if (updatedTeacher.getSchoolIds() != null && !updatedTeacher.getSchoolIds().isEmpty()) {
            Set<School> schools = new HashSet<>(schoolRepo.findAllById(updatedTeacher.getSchoolIds()));
            teacherToUpdate.setSchools(schools);

            for (School school : schools) {
                school.getTeachers().add(teacherToUpdate);
            }
        } else {
            teacherToUpdate.getSchools().clear();  // If null, clear the association
        }

        // Update subjects (handle null or empty lists)
        if (updatedTeacher.getSubjectIds() != null && !updatedTeacher.getSubjectIds().isEmpty()) {
            Set<Subject> subjects = new HashSet<>(subjectRepo.findAllById(updatedTeacher.getSubjectIds()));
            teacherToUpdate.setSubjects(subjects);

            for (Subject subject : subjects) {
                subject.getTeachers().add(teacherToUpdate);
            }
        } else {
            teacherToUpdate.getSubjects().clear();  // If null, clear the association
        }
        return teacherRepo.save(teacherToUpdate);
    }




    /**
     * Validates that the account role is "teacher".
     *
     * @param user the account to validate.
     * @throws IllegalArgumentException if the account role is not "teacher".
     */
    private void validateRoleIsTeacher(User user) {
        //had to add the casting after the 'many to many' relationship change for acc and accRole
        boolean isTeacher = user.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_TEACHER"));

        if (!isTeacher) {
            throw new IllegalArgumentException("Role must be teacher to assign account to a teacher");
        }
    }

    /**
     * Deletes an existing {@link Teacher} identified by its ID.
     *
     * @param id the ID of the teacher to delete.
     * @throws EntityNotFoundException if the teacher with the specified ID is not found.
     */
    public void deleteTeacherById(long id) {
        if (!doesTeacherExist(id)) {
            throw new EntityNotFoundException("Teacher not found with id: " + id);
        }

        Teacher teacherToDelete = findTeacherById(id);

        // Remove this teacher from all associated schools without deleting the schools
        for (School school : teacherToDelete.getSchools()) {
            school.getTeachers().remove(teacherToDelete);
            // Persist the changes to ensure associations are updated
            schoolRepo.save(school);
        }

        // Remove the teacher from all associated subjects
        for (Subject subject : teacherToDelete.getSubjects()) {
            subject.getTeachers().remove(teacherToDelete);
            subjectRepo.save(subject);
        }
        // Now delete the teacher
        teacherRepo.deleteById(id);
    }


    /**
     * Retrieves an existing {@link Teacher} identified by its ID.
     *
     * @param id the ID of the teacher to retrieve.
     * @return the teacher with the specified ID.
     * @throws EntityNotFoundException if the teacher with the specified ID is not found.
     */
    public Teacher findTeacherById(long id) {
        return teacherRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Teacher not found with id: " + id));
    }

    /**
     * Retrieves all existing {@link Teacher} entities.
     *
     * @return a list of all teachers.
     */
    public List<Teacher> findAllTeachers() {
        return teacherRepo.findAll();
    }

    /**
     * Checks if a teacher with the specified ID exists.
     *
     * @param id the ID of the teacher to check.
     * @return {@code true} if the teacher exists, {@code false} otherwise.
     */
    public boolean doesTeacherExist(long id) {
        return teacherRepo.existsById(id);
    }


    /**
     * Gets all the grades of students from the classes taught by the teacher.
     *
     * @param teacherId the teacher entity
     * @return a list of all grades for students taught by this teacher.
     */
    public List<Grade> getStudentGrades(long teacherId) {
        Teacher teacher = findTeacherById(teacherId);
        Set<Student> students = getAllStudentsTaughtByTeacher(teacher);
        return students.stream()
                .flatMap(student -> student.getGrades().stream())
                .collect(Collectors.toList());
    }

    /**
     * Gets all the absences of students from the classes taught by the teacher.
     *
     * @param teacherId the teacher entity
     * @return a list of all absences for students taught by this teacher.
     */
    public List<Absence> getStudentAbsences(long teacherId) {
        Teacher teacher = findTeacherById(teacherId);
        Set<Student> students = getAllStudentsTaughtByTeacher(teacher);
        return students.stream()
                .flatMap(student -> student.getAbsences().stream())
                .collect(Collectors.toList());
    }

    /**
     * Allows a teacher to add an absence for a student, but only if the student is in one of the teacher's classes.
     *
     * @param teacherId  the teacher who is adding the absence
     * @param absenceDTO the absence details
     * @return the saved absence
     * @throws EntityNotFoundException if the student is not in one of the teacher's classes
     */
    public Absence addAbsenceForStudent(long teacherId, AbsenceDTO absenceDTO) {

        Teacher teacher = findTeacherById(teacherId);
        Set<Student> teacherStudents = getAllStudentsTaughtByTeacher(teacher);

        Student student = studentService.findStudentById(absenceDTO.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + absenceDTO.getStudentId()));

        // Step 3: Check if the student is taught by the teacher
        if (!teacherStudents.contains(student)) {
            throw new IllegalArgumentException("Teacher is not allowed to add an absence for a student they do not teach.");
        }

        // Step 4: If the student is valid, create the absence
        Absence absence = new Absence();
        absence.setStudent(student);
        absence.setDateAdded(LocalDateTime.now());
        absence.setDateOfAbsence(absenceDTO.getDateOfAbsence());
        absence.setAbsenceState(absenceDTO.getAbsenceState());
        absence.setAbsenceType(absenceDTO.getAbsenceType());

        // Step 5: Save the absence using the AbsenceService
        return absenceService.saveAbsence(absenceDTO);
    }


    /**
     * Allows a teacher to add a grade for a student, but only if the student is in one of the teacher's classes.
     *
     * @param teacherId  the teacher who is adding the absence
     * @param gradeDTO the grade details
     * @return the saved grade
     * @throws EntityNotFoundException if the student is not in one of the teacher's classes
     */
    public Grade addGradeForStudent(long teacherId, GradeDTO gradeDTO) {

        Teacher teacher = findTeacherById(teacherId);
        Set<Student> teacherStudents = getAllStudentsTaughtByTeacher(teacher);

        Student student = studentService.findStudentById(gradeDTO.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + gradeDTO.getStudentId()));

        if (!teacherStudents.contains(student)) {
            throw new IllegalArgumentException("Teacher is not allowed to add a grade for a student they do not teach.");
        }

        Subject subject = subjectService.findSubjectById(gradeDTO.getSubjectId());
        Term term = termService.findTermById(gradeDTO.getTermId());

        Grade grade = new Grade();
        grade.setStudent(student);
        grade.setGrade(gradeDTO.getGrade());
        grade.setGradeType(gradeDTO.getGradeType());
        grade.setDateOfGrade(gradeDTO.getDateOfGrade());
        grade.setSubject(subject);
        grade.setTerm(term);

        return gradeService.saveGrade(gradeDTO);

    }

    /**
     * Retrieves all students that are in the classes taught by the given teacher.
     *
     * @param teacher the teacher entity
     * @return a set of all students taught by the teacher
     */
    private Set<Student> getAllStudentsTaughtByTeacher(Teacher teacher) {

        Set<Schedule> schedules = teacher.getSchedule();

        Set<Klass> klasses = schedules.stream()
                .map(Schedule::getKlass)
                .collect(Collectors.toSet());

        return klasses.stream()
                .flatMap(klass -> klass.getStudents().stream())
                .collect(Collectors.toSet());
    }

}
