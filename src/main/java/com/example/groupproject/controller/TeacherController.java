package com.example.groupproject.controller;

import com.example.groupproject.dto.AbsenceDTO;
import com.example.groupproject.dto.GradeDTO;
import com.example.groupproject.dto.TeacherDTO;
import com.example.groupproject.model.*;
import com.example.groupproject.service.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    private final TeacherService teacherService;
    @Autowired
    TeacherController(TeacherService teacherService){
        this.teacherService = teacherService;
    }
    @Autowired
    private KlassService klassService;
    @Autowired
    private GradeService gradeService;
    @Autowired
    private AbsenceService absenceService;
    @Autowired
    private StudentService studentService;

    @PostMapping("/add")
    public ResponseEntity<Teacher> postTeacher(@RequestBody TeacherDTO teacherDTO){
        Teacher teacher = teacherService.saveTeacher(teacherDTO);
        return ResponseEntity.ok(teacher);
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<Teacher> patchTeacher(@PathVariable Long id, @RequestBody TeacherDTO teacherDTO){
        Teacher teacher = teacherService.updateTeacherById(id, teacherDTO);
        return ResponseEntity.ok(teacher);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTeacher(@PathVariable Long id){
        try{
            teacherService.deleteTeacherById(id);
            return ResponseEntity.ok("The Teacher has been deleted");
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/fetch/all")
    public ResponseEntity<List<Teacher>> fetchAll(){
        List<Teacher> teachers= teacherService.findAllTeachers();
        return ResponseEntity.ok(teachers);
    }

    @GetMapping("/fetch/{id}")
    public ResponseEntity<Teacher> fetchById(@PathVariable long id){
        Teacher teacher = teacherService.findTeacherById(id);
        return ResponseEntity.ok(teacher);
    }

    @GetMapping("fetch/studentGrades/{id}")
    public ResponseEntity<List<Grade>> getStudentGrades(@PathVariable long id)
    {
        Teacher teacher = teacherService.findTeacherById(id);
        List<Grade> grades = teacherService.getStudentGrades(id);
        return ResponseEntity.ok(grades);
    }

    @GetMapping("fetch/studentAbsences/{id}")
    public ResponseEntity<List<Absence>> getStudentAbsences(@PathVariable long id)
    {
        Teacher teacher = teacherService.findTeacherById(id);
        List<Absence> absences = teacherService.getStudentAbsences(id);
        return ResponseEntity.ok(absences);
    }

    @PostMapping("addAbsence/{id}")
    public ResponseEntity<Absence> addAbsence(@PathVariable Long id, @RequestBody AbsenceDTO absenceDTO){
        Absence absence = teacherService.addAbsenceForStudent(id, absenceDTO);
        return ResponseEntity.ok(absence);
    }

    @PostMapping("addGrade/{id}")
    public ResponseEntity<Grade> addGrade(@PathVariable Long id, @RequestBody GradeDTO gradeDTO){
        Grade grade = teacherService.addGradeForStudent(id, gradeDTO);
        return ResponseEntity.ok(grade);
    }

}
