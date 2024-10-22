package com.example.groupproject.controller;

import com.example.groupproject.dto.SchoolDTO;
import com.example.groupproject.model.School;
import com.example.groupproject.model.Teacher;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.groupproject.service.SchoolService;

import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/school")
public class SchoolController {
    private final SchoolService schoolService;
    @Autowired
    SchoolController(SchoolService schoolService){
        this.schoolService = schoolService;
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<School> postSchool(@RequestBody SchoolDTO schoolDTO){
        School school = schoolService.saveSchool(schoolDTO);
        return ResponseEntity.ok(school);
    }

    @PatchMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<School> patchSchool(@PathVariable long id, @RequestBody SchoolDTO SchoolDTO){
        School school = schoolService.updateSchoolById(id,SchoolDTO);
        return ResponseEntity.ok(school);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteSchool(@PathVariable long id){
        try{
            schoolService.deleteSchoolById(id);
            return ResponseEntity.ok("The school with ID " + id + " has been deleted");
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/fetch/all")
    public ResponseEntity<List<School>> fetchAll(){
        List<School> schools = schoolService.findAllSchools();
        return ResponseEntity.ok(schools);
    }

    @GetMapping("/fetch/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<School> fetchById(@PathVariable long id){
        School school = schoolService.findSchoolById(id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(school);
    }

    @GetMapping("/fetch/{id}/teachers")
    public ResponseEntity<Set<Teacher>>fetchCompanyteachers(@PathVariable long id){
        Set<Teacher> teachers = schoolService.fetchTeachers(id);
        return ResponseEntity.ok(teachers);
    }
}
