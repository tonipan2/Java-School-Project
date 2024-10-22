package com.example.groupproject.controller;

import com.example.groupproject.dto.ScheduleDTO;
import com.example.groupproject.model.Schedule;
import com.example.groupproject.service.ScheduleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;
    @Autowired
    ScheduleController(ScheduleService scheduleService){
        this.scheduleService = scheduleService;
    }

    @PostMapping("/add")
    public ResponseEntity<Schedule> postWeeklySchedule(@RequestBody ScheduleDTO scheduleDTO){
        Schedule schedule = scheduleService.saveWeeklySchedule(scheduleDTO);
        return ResponseEntity.ok(schedule);
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<Schedule> patchWeeklySchedule(@PathVariable Long id, @RequestBody ScheduleDTO scheduleDTO){
        Schedule schedule = scheduleService.updateWeeklyScheduleById(id, scheduleDTO);
        return ResponseEntity.ok(schedule);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteWeeklySchedule(@PathVariable Long id){
        try{
            scheduleService.deleteWeeklyScheduleById(id);
            return ResponseEntity.ok("The WeeklySchedule has been deleted");
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/fetch/all")
    public ResponseEntity<List<Schedule>> fetchAll(){
        List<Schedule> schedules = scheduleService.findAllWeeklySchedules();
        return ResponseEntity.ok(schedules);
    }
    @GetMapping("/fetch/{id}")
    public ResponseEntity<Schedule> fetchById(@PathVariable long id){
        Schedule schedule = scheduleService.findWeeklyScheduleById(id);
        return ResponseEntity.ok(schedule);
    }

}
