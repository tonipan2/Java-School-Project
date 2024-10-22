package com.example.groupproject.controller;

import com.example.groupproject.dto.HeadmasterDTO;
import com.example.groupproject.model.Headmaster;
import com.example.groupproject.service.HeadmasterService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/headmaster")
public class HeadmasterController {
    private final HeadmasterService headmasterService;
    @Autowired
    HeadmasterController(HeadmasterService headmasterService){
        this.headmasterService = headmasterService;
    }

    @PostMapping("/add")
    public ResponseEntity<Headmaster> postPrincipal(@RequestBody HeadmasterDTO headmasterDTO){
        Headmaster headmaster = headmasterService.savePrincipal(headmasterDTO);
        return ResponseEntity.ok(headmaster);
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<Headmaster> patchPrincipal(@PathVariable Long id, @RequestBody HeadmasterDTO headmasterDTO){
        Headmaster headmaster = headmasterService.updatePrincipalById(id, headmasterDTO);
        return ResponseEntity.ok(headmaster);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePrincipal(@PathVariable Long id){
        try{
            headmasterService.deletePrincipalById(id);
            return ResponseEntity.ok("The Headmaster has been deleted");
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/fetch/all")
    public ResponseEntity<List<Headmaster>> fetchAll(){
        List<Headmaster> headmasters = headmasterService.findAllPrincipals();
        return ResponseEntity.ok(headmasters);
    }
    @GetMapping("/fetch/{id}")
    public ResponseEntity<Headmaster> fetchById(@PathVariable long id){
        Headmaster headmaster = headmasterService.findPrincipalById(id);
        return ResponseEntity.ok(headmaster);
    }

}
