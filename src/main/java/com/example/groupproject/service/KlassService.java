package com.example.groupproject.service;

import com.example.groupproject.dto.KlassDTO;
import com.example.groupproject.model.Klass;
import com.example.groupproject.model.School;
import com.example.groupproject.repository.KlassRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class KlassService {
    private final KlassRepository klassRepo;
    private final SchoolService schoolService;

    @Autowired
    public KlassService(KlassRepository klassRepo, SchoolService schoolService) {
        this.klassRepo = klassRepo;
        this.schoolService = schoolService;
    }
    /**
     * Saves a new {@link Klass} based on the provided {@link KlassDTO}.
     *
     * @param klassDTO the data transfer object representing the new klass.
     */
    public Klass saveKlass(KlassDTO klassDTO) {
        if (klassDTO == null) {
            throw new IllegalArgumentException("KlassDTO cannot be null");
        }

        if (klassDTO.getSchoolId() <= 0) {
            throw new IllegalArgumentException("Invalid school ID: " + klassDTO.getSchoolId());
        }

        // Find the school by ID
        School school = schoolService.findSchoolById(klassDTO.getSchoolId());
        if (school == null) {
            throw new EntityNotFoundException("School not found with id: " + klassDTO.getSchoolId());
        }

        // Create a new Klass entity and set the school
        Klass klassToSave = new Klass();
        klassToSave.setName(klassDTO.getName());
        klassToSave.setSchool(school);  // Assign the school to the klass

        return klassRepo.save(klassToSave);
    }


    /**
     * Updates an existing {@link Klass} identified by its ID.
     *
     * @param klassId      the ID of the klass to update.
     * @param updatedKlass the updated data transfer object for the klass.
     */
    public Klass updateKlassById(long klassId, KlassDTO updatedKlass) {

        // Find the existing Klass by ID
        Klass klassToUpdate = findKlassById(klassId);

        // Update the Klass name if provided
        if (updatedKlass.getName() != null && !updatedKlass.getName().isEmpty()) {
            klassToUpdate.setName(updatedKlass.getName());
        }

        // Update the Klass school if a new schoolId is provided
        if (updatedKlass.getSchoolId() > 0) {
            School newSchool = schoolService.findSchoolById(updatedKlass.getSchoolId());
            if (newSchool != null) {
                klassToUpdate.setSchool(newSchool);
            }
        }

        // Save the updated Klass entity
        return klassRepo.save(klassToUpdate);
    }



    /**
     * Deletes an existing {@link Klass} identified by its ID.
     *
     * @param id the ID of the klass to delete.
     * @throws EntityNotFoundException if the klass with the specified ID is not found.
     */
    public void deleteKlassById(long id) {
        if (!doesKlassExist(id)){
            throw new EntityNotFoundException("Klass not found with id: " + id);
        }
        klassRepo.deleteById(id);
    }

    /**
     * Retrieves an existing {@link Klass} identified by its ID.
     *
     * @param id the ID of the klass to retrieve.
     * @return the klass with the specified ID.
     * @throws EntityNotFoundException if the klass with the specified ID is not found.
     */
    public Klass findKlassById(long id) {
        return klassRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Klass not found with id: " + id));
    }

    /**
     * Retrieves all existing {@link Klass} entities.
     *
     * @return a list of all klasss.
     */
    public List<Klass> findAllKlasss() {
        return klassRepo.findAll();
    }

    /**
     * Checks if a klass with the specified ID exists.
     *
     * @param id the ID of the klass to check.
     * @return {@code true} if the klass exists, {@code false} otherwise.
     */
    public boolean doesKlassExist(long id) {
        return klassRepo.existsById(id);
    }

}
