package com.example.groupproject.controller;

import com.example.groupproject.dto.AbsenceDTO;
import com.example.groupproject.model.Absence;
import com.example.groupproject.service.AbsenceService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/absence")
public class AbsenceController {
    private final AbsenceService absenceService;
    @Autowired
    AbsenceController(AbsenceService absenceService){
        this.absenceService = absenceService;
    }

    @PostMapping("/add")
    public ResponseEntity<Absence> postAbsence(@RequestBody AbsenceDTO absenceDTO){
        Absence absence = absenceService.saveAbsence(absenceDTO);
        return ResponseEntity.ok(absence);
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<Absence> patchAbsence(@PathVariable Long id, @RequestBody AbsenceDTO absenceDTO){
        Absence absence = absenceService.updateAbsenceById(id, absenceDTO);
        return ResponseEntity.ok(absence);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAbsence(@PathVariable Long id){
        try{
            absenceService.deleteAbsenceById(id);
            return ResponseEntity.ok("The Absence has been deleted");
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/fetch/all")
    public ResponseEntity<List<Absence>> fetchAll(){
        List<Absence> absences= absenceService.findAllAbsences();
        return ResponseEntity.ok(absences);
    }
    @GetMapping("/fetch/{id}")
    public ResponseEntity<Absence> fetchById(@PathVariable long id){
        Absence absence = absenceService.findAbsenceById(id);
        return ResponseEntity.ok(absence);
    }

}
