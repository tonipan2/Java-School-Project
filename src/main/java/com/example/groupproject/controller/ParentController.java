package com.example.groupproject.controller;

import com.example.groupproject.dto.ParentDTO;
import com.example.groupproject.dto.StudentDTO;
import com.example.groupproject.model.Absence;
import com.example.groupproject.model.Grade;
import com.example.groupproject.model.Parent;
import com.example.groupproject.model.Student;
import com.example.groupproject.service.ParentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/parent")
public class ParentController {
    private final ParentService parentService;
    @Autowired
    ParentController(ParentService parentService){
        this.parentService = parentService;
    }

    @PostMapping("/add")
    public ResponseEntity<Parent> postParent(@RequestBody ParentDTO parentDTO){
        Parent parent = parentService.saveParent(parentDTO);
        return ResponseEntity.ok(parent);
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<Parent> patchParent(@PathVariable Long id, @RequestBody ParentDTO parentDTO){
        Parent parent = parentService.updateParentById(id, parentDTO);
        return ResponseEntity.ok(parent);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteParent(@PathVariable Long id){
        try{
            parentService.deleteParentById(id);
            return ResponseEntity.ok("The Parent has been deleted");
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/fetch/all")
    public ResponseEntity<List<Parent>> fetchAll(){
        List<Parent> parents= parentService.findAllParents();
        return ResponseEntity.ok(parents);

    }
    @GetMapping("/fetch/{id}")
    public ResponseEntity<Parent> fetchById(@PathVariable long id){
        Parent parent = parentService.findParentById(id);
        return ResponseEntity.ok(parent);
    }

    @GetMapping("/fetch/studentGrades/{id}")
    public ResponseEntity<Map<StudentDTO, Set<Grade>>> getStudentGrades(@PathVariable long id) {
        Map<Student, Set<Grade>> grades = parentService.getStudentGrades(id);
        Map<StudentDTO, Set<Grade>> result = new HashMap<>();
        for (Map.Entry<Student, Set<Grade>> entry : grades.entrySet()) {
            Student student = entry.getKey();
            StudentDTO studentDTO = new StudentDTO(student.getId(), student.getUser().getId(), student.getSchool().getId(), student.getKlass().getId(), null);
            result.put(studentDTO, entry.getValue());
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/fetch/studentAbsences/{id}")
    public ResponseEntity<Map<StudentDTO, Set<Absence>>> getStudentAbsences(@PathVariable long id){
        Map<Student, Set<Absence>> absences = parentService.getStudentAbsences(id);
        Map<StudentDTO, Set<Absence>> result = new HashMap<>();
        for(Map.Entry<Student, Set<Absence>> entry : absences.entrySet()){
            Student student = entry.getKey();
            StudentDTO studentDTO = new StudentDTO(student.getId(), student.getUser().getId(), student.getSchool().getId(), student.getKlass().getId(), null);
            result.put(studentDTO, entry.getValue());
        }
        return ResponseEntity.ok(result);
    }
}
