package com.example.groupproject.controller;

import com.example.groupproject.dto.TermDTO;
import com.example.groupproject.model.Term;
import com.example.groupproject.service.TermService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/term")
public class TermController {
    private final TermService termService;
    @Autowired
    TermController(TermService termService){
        this.termService = termService;
    }

    @PostMapping("/add")
    public ResponseEntity<Term> postTerm(@RequestBody TermDTO termDTO){
        Term term = termService.saveTerm(termDTO);
        return ResponseEntity.ok(term);
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<Term> patchTerm(@PathVariable Long id, @RequestBody TermDTO termDTO){
        Term term = termService.updateTermById(id, termDTO);
        return ResponseEntity.ok(term);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTerm(@PathVariable Long id){
        try{
            termService.deleteTermById(id);
            return ResponseEntity.ok("The Term has been deleted");
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/fetch/all")
    public ResponseEntity<List<Term>> fetchAll(){
        List<Term> terms= termService.findAllTerms();
        return ResponseEntity.ok(terms);
    }
    @GetMapping("/fetch/{id}")
    public ResponseEntity<Term> fetchById(@PathVariable long id){
        Term term = termService.findTermById(id);
        return ResponseEntity.ok(term);
    }

}
