package com.example.groupproject.service;

import com.example.groupproject.dto.ScheduleDTO;
import com.example.groupproject.model.*;
import com.example.groupproject.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ScheduleService {
    private final ScheduleRepository weeklyScheduleRepo;
    private final KlassRepository klassRepository;

    private final SubjectRepository subjectRepository;

    private final TeacherRepository teacherRepository;


    private final TermRepository termRepository;


    @Autowired
    public ScheduleService(ScheduleRepository weeklyScheduleRepo, KlassRepository klassRepository, SubjectRepository subjectRepository, TeacherRepository teacherRepository, TermRepository termRepository) {
        this.weeklyScheduleRepo = weeklyScheduleRepo;
        this.klassRepository = klassRepository;
        this.subjectRepository = subjectRepository;
        this.teacherRepository = teacherRepository;
        this.termRepository = termRepository;
    }
    /**
     * Saves a new {@link Schedule} based on the provided {@link ScheduleDTO}.
     *
     * @param scheduleDTO the data transfer object representing the new weeklySchedule.
     */public Schedule saveWeeklySchedule(ScheduleDTO scheduleDTO) {
        Schedule scheduleToSave = new Schedule();

        // Set dayOfTheWeek and numberOfPeriod
        scheduleToSave.setDayOfTheWeek(scheduleDTO.getDayOfTheWeek());
        scheduleToSave.setNumberOfPeriod(scheduleDTO.getNumberOfPeriod());

        // Fetch and set Klass
        Klass klass = klassRepository.findById(scheduleDTO.getKlassId())
                .orElseThrow(() -> new EntityNotFoundException("Klass not found"));
        scheduleToSave.setKlass(klass);

        // Fetch and set Subject
        Subject subject = subjectRepository.findById(scheduleDTO.getSubjectId())
                .orElseThrow(() -> new EntityNotFoundException("Subject not found"));
        scheduleToSave.setSubject(subject);

        // Fetch and set Teacher
        Teacher teacher = teacherRepository.findById(scheduleDTO.getTeacherId())
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found"));
        scheduleToSave.setTeacher(teacher);

        // Fetch and set Term
        Term term = termRepository.findById(scheduleDTO.getTermId())
                .orElseThrow(() -> new EntityNotFoundException("Term not found"));
        scheduleToSave.setTerm(term);

        // Save the schedule
        return weeklyScheduleRepo.save(scheduleToSave);
    }

    /**
     * Updates an existing {@link Schedule} identified by its ID.
     *
     * @param weeklyScheduleId      the ID of the weeklySchedule to update.
     * @param updatedWeeklySchedule the updated data transfer object for the weeklySchedule.
     */
    @Transactional
    public Schedule updateWeeklyScheduleById(long weeklyScheduleId, ScheduleDTO updatedWeeklySchedule) {
        Schedule scheduleToUpdate = findWeeklyScheduleById(weeklyScheduleId);

        // Update the basic fields
        String newDay = updatedWeeklySchedule.getDayOfTheWeek();
        String newPeriod = updatedWeeklySchedule.getNumberOfPeriod();
        scheduleToUpdate.setDayOfTheWeek(newDay);
        scheduleToUpdate.setNumberOfPeriod(newPeriod);

        // Handle Teacher update if Teacher ID is provided
        if (updatedWeeklySchedule.getTeacherId() != null) {
            Teacher teacher = teacherRepository.findById(updatedWeeklySchedule.getTeacherId())
                    .orElseThrow(() -> new EntityNotFoundException("Teacher not found"));
            scheduleToUpdate.setTeacher(teacher);
        }

        // Handle Term update if Term ID is provided
        if (updatedWeeklySchedule.getTermId() != null) {
            Term term = termRepository.findById(updatedWeeklySchedule.getTermId())
                    .orElseThrow(() -> new EntityNotFoundException("Term not found"));
            scheduleToUpdate.setTerm(term);
        }

        // Handle Klass update if Klass ID is provided
        if (updatedWeeklySchedule.getKlassId() != null) {
            Klass klass = klassRepository.findById(updatedWeeklySchedule.getKlassId())
                    .orElseThrow(() -> new EntityNotFoundException("Klass not found"));
            scheduleToUpdate.setKlass(klass);
        }

        // Handle Subject update if Subject ID is provided
        if (updatedWeeklySchedule.getSubjectId() != null) {
            Subject subject = subjectRepository.findById(updatedWeeklySchedule.getSubjectId())
                    .orElseThrow(() -> new EntityNotFoundException("Subject not found"));
            scheduleToUpdate.setSubject(subject);
        }

        // Save the updated schedule
        return weeklyScheduleRepo.save(scheduleToUpdate);
    }


    /**
     * Deletes an existing {@link Schedule} identified by its ID.
     *
     * @param id the ID of the schedule to delete.
     * @throws EntityNotFoundException if the schedule with the specified ID is not found.
     */
    public void deleteWeeklyScheduleById(long id) {
        if (!doesWeeklyScheduleExist(id)){
            throw new EntityNotFoundException("Schedule not found with id: " + id);
        }
        weeklyScheduleRepo.deleteById(id);
    }

    /**
     * Retrieves an existing {@link Schedule} identified by its ID.
     *
     * @param id the ID of the weeklySchedule to retrieve.
     * @return the weeklySchedule with the specified ID.
     * @throws EntityNotFoundException if the weeklySchedule with the specified ID is not found.
     */
    public Schedule findWeeklyScheduleById(long id) {
        return weeklyScheduleRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("WeeklySchedule not found with id: " + id));
    }

    /**
     * Retrieves all existing {@link Schedule} entities.
     *
     * @return a list of all weeklySchedules.
     */
    public List<Schedule> findAllWeeklySchedules() {
        return weeklyScheduleRepo.findAll();
    }

    /**
     * Checks if a weeklySchedule with the specified ID exists.
     *
     * @param id the ID of the weeklySchedule to check.
     * @return {@code true} if the weeklySchedule exists, {@code false} otherwise.
     */
    public boolean doesWeeklyScheduleExist(long id) {
        return weeklyScheduleRepo.existsById(id);
    }

}
