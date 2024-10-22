package com.example.groupproject.controller;

import com.example.groupproject.dto.KlassDTO;
import com.example.groupproject.model.Klass;
import com.example.groupproject.service.KlassService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/klass")
public class KlassController {
    private final KlassService klassService;
    @Autowired
    KlassController(KlassService klassService) {
        this.klassService = klassService;
    }

    @PostMapping("/add")
    public ResponseEntity<Klass> postKlass(@RequestBody KlassDTO klassDTO){
        Klass klass = klassService.saveKlass(klassDTO);
        return ResponseEntity.ok(klass);
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<Klass> patchKlass(@PathVariable Long id, @RequestBody KlassDTO klassDTO){
        Klass klass = klassService.updateKlassById(id, klassDTO);
        return ResponseEntity.ok(klass);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteKlass(@PathVariable Long id){
        try{
            klassService.deleteKlassById(id);
            return ResponseEntity.ok("The klass with ID " + id + " has been deleted");
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/fetch/all")
    public ResponseEntity<List<Klass>> fetchAll(){
        List<Klass> klasss = klassService.findAllKlasss();
        return ResponseEntity.ok(klasss);
    }
    @GetMapping("/fetch/{id}")
    public ResponseEntity<Klass> fetchById(@PathVariable long id){
        Klass klass= klassService.findKlassById(id);
        return ResponseEntity.ok(klass);
    }
}
