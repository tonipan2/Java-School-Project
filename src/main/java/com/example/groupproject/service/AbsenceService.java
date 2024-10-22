package com.example.groupproject.service;

import com.example.groupproject.dto.*;
import com.example.groupproject.model.*;
import com.example.groupproject.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AbsenceService {
    @Autowired
    private final AbsenceRepository absenceRepo;
    @Autowired
    @Lazy
    private StudentService studentService;

    @Autowired
    public AbsenceService(AbsenceRepository absenceRepo) {
        this.absenceRepo = absenceRepo;
    }

    /**
     * Saves a new {@link Absence} based on the provided {@link AbsenceDTO}.
     *
     * @param absenceDTO the data transfer object representing the new absence.
     * @throws DataIntegrityViolationException if the email or username already exists.
     */
    public Absence saveAbsence(AbsenceDTO absenceDTO) {
        // Fetch student entity by ID
        Optional<Student> studentOptional = studentService.findStudentById(absenceDTO.getStudentId());
        Student student = studentOptional.orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + absenceDTO.getStudentId()));

        Absence absenceToSave = new Absence();
        absenceToSave.setDateAdded(LocalDateTime.now()); // Set current date and time
        absenceToSave.setDateOfAbsence(absenceDTO.getDateOfAbsence());
        absenceToSave.setAbsenceState(absenceDTO.getAbsenceState());
        absenceToSave.setAbsenceType(absenceDTO.getAbsenceType());
        absenceToSave.setStudent(student);

        return absenceRepo.save(absenceToSave);
    }
    /**
     * Updates an existing {@link Absence} identified by its ID.
     *
     * @param absenceId      the ID of the absence to update.
     * @param updatedAbsence the updated data transfer object for the absence.*/

    public Absence updateAbsenceById(long absenceId, AbsenceDTO updatedAbsence) {
        Absence absenceToUpdate =  findAbsenceById(absenceId);

        AbsenceState newAbsenceState = updatedAbsence.getAbsenceState();
        AbsenceType newAbsenceType = updatedAbsence.getAbsenceType();

        absenceToUpdate.setAbsenceState(newAbsenceState);
        absenceToUpdate.setAbsenceType(newAbsenceType);
        return absenceRepo.save( absenceToUpdate);
    }

    /**
     * Deletes an existing {@link Absence} identified by its ID.
     *
     * @param id the ID of the absence to delete.
     * @throws EntityNotFoundException if the absence with the specified ID is not found.
*/
    public void deleteAbsenceById(long id) {
        if (!doesAbsenceExist(id)){
            throw new EntityNotFoundException("Absence not found with id: " + id);
        }
        absenceRepo.deleteById(id);
    }

    public boolean doesAbsenceExist(long id) {
        return absenceRepo.existsById(id);
    }


    /**
     * Retrieves all existing {@link Absence} entities.
     *
     * @return a list of all absences.
     */

    public List<Absence> findAllAbsences() {
        return  absenceRepo.findAll();
    }

    public Absence findAbsenceById(long id) {
        return  absenceRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Absence not found with id: " + id));
    }

    //public List<Absence> findAbsencesByStudentId(long studentId) {
      //  return absenceRepo.findAbsencesByStudent(studentId);
    //}


}
