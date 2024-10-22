package com.example.groupproject.controller;

import com.example.groupproject.dto.SubjectDTO;
import com.example.groupproject.model.Subject;
import com.example.groupproject.service.SubjectService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subject")
public class SubjectController {
    private final SubjectService subjectService;
    @Autowired
    SubjectController(SubjectService subjectService){
        this.subjectService = subjectService;
    }

    @PostMapping("/add")
    public ResponseEntity<Subject> postSubject(@RequestBody SubjectDTO subjectDTO){
        Subject subject = subjectService.saveSubject(subjectDTO);
        return ResponseEntity.ok(subject);
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<Subject> patchSubject(@PathVariable Long id, @RequestBody SubjectDTO subjectDTO){
        Subject subject = subjectService.updateSubjectById(id, subjectDTO);
        return ResponseEntity.ok(subject);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSubject(@PathVariable Long id){
        try{
            subjectService.deleteSubjectById(id);
            return ResponseEntity.ok("The Subject has been deleted");
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/fetch/all")
    public ResponseEntity<List<Subject>> fetchAll(){
        List<Subject> subjects= subjectService.findAllSubjects();
        return ResponseEntity.ok(subjects);
    }
    @GetMapping("/fetch/{id}")
    public ResponseEntity<Subject> fetchById(@PathVariable long id){
        Subject subject = subjectService.findSubjectById(id);
        return ResponseEntity.ok(subject);
    }

}
