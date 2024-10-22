package com.example.groupproject.controller;

import com.example.groupproject.dto.GradeDTO;
import com.example.groupproject.model.Grade;
import com.example.groupproject.service.GradeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grade")
public class GradeController {
    private final GradeService gradeService;
    @Autowired
    GradeController(GradeService gradeService){
        this.gradeService = gradeService;
    }

    @PostMapping("/add")
    public ResponseEntity<Grade> postGrade(@RequestBody GradeDTO gradeDTO){
        Grade grade = gradeService.saveGrade(gradeDTO);
        return ResponseEntity.ok(grade);
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<Grade> patchGrade(@PathVariable Long id, @RequestBody GradeDTO gradeDTO){
        Grade grade = gradeService.updateGradeById(id, gradeDTO);
        return ResponseEntity.ok(grade);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteGrade(@PathVariable Long id){
        try{
            gradeService.deleteGradeById(id);
            return ResponseEntity.ok("The Grade has been deleted");
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/fetch/all")
    public ResponseEntity<List<Grade>> fetchAll(){
        List<Grade> grades= gradeService.findAllGrades();
        return ResponseEntity.ok(grades);
    }
    @GetMapping("/fetch/{id}")
    public ResponseEntity<Grade> fetchById(@PathVariable long id){
        Grade grade = gradeService.findGradeById(id);
        return ResponseEntity.ok(grade);
    }



}
