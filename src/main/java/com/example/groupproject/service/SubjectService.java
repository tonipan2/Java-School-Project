package com.example.groupproject.service;

import com.example.groupproject.dto.SubjectDTO;
import com.example.groupproject.model.Subject;
import com.example.groupproject.repository.SubjectRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service

public class SubjectService {
    private final SubjectRepository subjectRepo;

    @Autowired
    public SubjectService(SubjectRepository subjectRepo) {
        this.subjectRepo = subjectRepo;    }
    /**
     * Saves a new {@link Subject} based on the provided {@link SubjectDTO}.
     *
     * @param subjectDTO the data transfer object representing the new subject.
     */
    public Subject saveSubject( SubjectDTO subjectDTO ) {
        Subject subjectToSave = new Subject();
        subjectToSave.setTitle(subjectDTO.getTitle());
        return subjectRepo.save(subjectToSave);
    }

    /**
     * Updates an existing {@link Subject} identified by its ID.
     *
     * @param subjectId      the ID of the subject to update.
     * @param updatedSubject the updated data transfer object for the subject.
     */
    public Subject updateSubjectById(long subjectId,  SubjectDTO updatedSubject) {
        Subject subjectToUpdate = findSubjectById(subjectId);
        String newName = updatedSubject.getTitle();
        subjectToUpdate.setTitle(newName);
        return subjectRepo.save(subjectToUpdate);
    }

    /**
     * Deletes an existing {@link Subject} identified by its ID.
     *
     * @param id the ID of the subject to delete.
     * @throws EntityNotFoundException if the subject with the specified ID is not found.
     */
    public void deleteSubjectById(long id) {
        if (!doesSubjectExist(id)){
            throw new EntityNotFoundException("Subject not found with id: " + id);
        }
        subjectRepo.deleteById(id);
    }

    /**
     * Retrieves an existing {@link Subject} identified by its ID.
     *
     * @param id the ID of the subject to retrieve.
     * @return the subject with the specified ID.
     * @throws EntityNotFoundException if the subject with the specified ID is not found.
     */
    public  Subject findSubjectById(long id) {
        return subjectRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Subject not found with id: " + id));
    }

    /**
     * Retrieves all existing {@link Subject} entities.
     *
     * @return a list of all subjects.
     */
    public List<Subject> findAllSubjects() {
        return subjectRepo.findAll();
    }

    /**
     * Checks if a subject with the specified ID exists.
     *
     * @param id the ID of the subject to check.
     * @return {@code true} if the subject exists, {@code false} otherwise.
     */
    public boolean doesSubjectExist(long id) {
        return subjectRepo.existsById(id);
    }

}
