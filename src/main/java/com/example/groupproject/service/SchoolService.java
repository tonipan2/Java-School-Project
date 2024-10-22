package com.example.groupproject.service;

import com.example.groupproject.model.Teacher;
import com.example.groupproject.repository.SchoolRepository;
import com.example.groupproject.dto.SchoolDTO;
import jakarta.persistence.EntityNotFoundException;
import com.example.groupproject.model.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class SchoolService {
    private final SchoolRepository schoolRepo;
    @Autowired
    public SchoolService(SchoolRepository schoolRepo) {
        this.schoolRepo = schoolRepo;
    }

    /**
     * Saves a new {@link School} based on the provided {@link SchoolDTO}.
     *
     * @param schoolDTO the data transfer object representing the new school.
     */
    public School saveSchool(SchoolDTO schoolDTO) {
        School schoolToSave = new School();
        schoolToSave.setName(schoolDTO.getName());
        schoolToSave.setAddress(schoolDTO.getAddress());
        return schoolRepo.save(schoolToSave);
    }

    /**
     * Updates an existing {@link School} identified by its ID.
     *
     * @param schoolId      the ID of the school to update.
     * @param updatedschool the updated data transfer object for the school.
     */
    public School updateSchoolById(long schoolId, SchoolDTO updatedschool) {
        School schoolToUpdate = findSchoolById(schoolId);
        String newName = updatedschool.getName();
        schoolToUpdate.setName(newName);
        return schoolRepo.save(schoolToUpdate);
    }

    /**
     * Deletes an existing {@link School} identified by its ID.
     *
     * @param id the ID of the school to delete.
     * @throws EntityNotFoundException if the school with the specified ID is not found.
     */
    public void deleteSchoolById(long id) {
        if (!doesSchoolExist(id)){
            throw new EntityNotFoundException("school not found with id: " + id);
        }
        schoolRepo.deleteById(id);
    }

    /**
     * Retrieves an existing {@link School} identified by its ID.
     *
     * @param id the ID of the school to retrieve.
     * @return the school with the specified ID.
     * @throws EntityNotFoundException if the school with the specified ID is not found.
     */
    public School findSchoolById(long id) {
        return schoolRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("school not found with id: " + id));
    }

    /**
     * Retrieves all existing {@link School} entities.
     *
     * @return a list of all schools.
     */
    public List<School> findAllSchools() {
        return schoolRepo.findAll();
    }

    /**
     * Fetches the employees associated with a specific school.
     *
     * @param schoolId the ID of the school.
     * @return the set of employees associated with the school.
     */
    public Set<Teacher>fetchTeachers(long schoolId){
        School school = findSchoolById(schoolId);
        return school.getTeachers();
    }


    /**
     * Checks if an school with the specified ID exists.
     *
     * @param id the ID of the school to check.
     * @return {@code true} if the school exists, {@code false} otherwise.
     */
    public boolean doesSchoolExist(long id) {
        return schoolRepo.existsById(id);
    }

}
