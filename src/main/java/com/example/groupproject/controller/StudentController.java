package com.example.groupproject.controller;

import com.example.groupproject.dto.StudentDTO;
import com.example.groupproject.model.Grade;
import com.example.groupproject.model.Student;
import com.example.groupproject.service.ParentService;
import com.example.groupproject.service.StudentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;
    @Autowired
    StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @PostMapping("/add")
    public ResponseEntity<Student> postStudent(@RequestBody StudentDTO studentDTO){
        Student student = studentService.saveStudent(studentDTO);
        return ResponseEntity.ok(student);
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<Student> patchStudent(@PathVariable Long id, @RequestBody StudentDTO studentDTO){
        Student student = studentService.updateStudentById(id, studentDTO);
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id){
        try{
            studentService.deleteStudentById(id);
            return ResponseEntity.ok("The Student has been deleted");
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/fetch/all")
    public ResponseEntity<List<Student>> fetchAll(){
        List<Student> students= studentService.findAllStudents();
        return ResponseEntity.ok(students);
    }
    @GetMapping("/fetch/{id}")
    public ResponseEntity<Student> fetchById(@PathVariable long id) {
        Student student = studentService.findStudentById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + id));
        return ResponseEntity.ok(student);
    }

    @GetMapping("fetch/studentGrades/{id}")
    public ResponseEntity<List<Grade>> getStudentGrades(@PathVariable long id)
    {
        List<Grade> grades = studentService.getStudentGrades(id);
        return ResponseEntity.ok(grades);
    }

}
