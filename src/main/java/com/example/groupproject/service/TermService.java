package com.example.groupproject.service;

import com.example.groupproject.dto.TermDTO;
import com.example.groupproject.model.Term;
import com.example.groupproject.model.TermType;
import com.example.groupproject.repository.TermRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class TermService {
    private final TermRepository termRepo;

    @Autowired
    public TermService(TermRepository termRepo) {
        this.termRepo = termRepo;
    }
    /**
     * Saves a new {@link Term} based on the provided {@link TermDTO}.
     *
     * @param termDTO the data transfer object representing the new term.
     */
    public Term saveTerm( TermDTO termDTO ) {
        Term termToSave = new Term();
        termToSave.setTermType(termDTO.getTermType());
        return termRepo.save(termToSave);
    }

    /**
     * Updates an existing {@link Term} identified by its ID.
     *
     * @param termId      the ID of the term to update.
     * @param updatedTerm the updated data transfer object for the term.
     */
    public Term updateTermById(long termId,  TermDTO updatedTerm) {
        Term termToUpdate = findTermById(termId);
        TermType newTerm = updatedTerm.getTermType();

        termToUpdate.setTermType(newTerm);
        return termRepo.save(termToUpdate);
    }

    /**
     * Deletes an existing {@link Term} identified by its ID.
     *
     * @param id the ID of the term to delete.
     * @throws EntityNotFoundException if the term with the specified ID is not found.
     */
    public void deleteTermById(long id) {
        if (!doesTermExist(id)){
            throw new EntityNotFoundException("Term not found with id: " + id);
        }
        termRepo.deleteById(id);
    }

    /**
     * Retrieves an existing {@link Term} identified by its ID.
     *
     * @param id the ID of the term to retrieve.
     * @return the term with the specified ID.
     * @throws EntityNotFoundException if the term with the specified ID is not found.
     */
    public  Term findTermById(long id) {
        return termRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Term not found with id: " + id));
    }

    /**
     * Retrieves all existing {@link Term} entities.
     *
     * @return a list of all terms.
     */
    public List<Term> findAllTerms() {
        return termRepo.findAll();
    }

    /**
     * Checks if a term with the specified ID exists.
     *
     * @param id the ID of the term to check.
     * @return {@code true} if the term exists, {@code false} otherwise.
     */
    public boolean doesTermExist(long id) {
        return termRepo.existsById(id);
    }

}
