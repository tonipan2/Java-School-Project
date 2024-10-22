package com.example.groupproject.controller;

import com.example.groupproject.dto.AdminDTO;
import com.example.groupproject.model.Admin;
import com.example.groupproject.service.AdminService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;
    @Autowired
    AdminController(AdminService adminService){
        this.adminService = adminService;
    }

    @PostMapping("/add")
    public ResponseEntity<Admin> postAdmin(@RequestBody AdminDTO adminDTO){
        Admin admin = adminService.saveAdmin(adminDTO);
        return ResponseEntity.ok(admin);
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<Admin> patchAdmin(@PathVariable Long id, @RequestBody AdminDTO adminDTO){
        Admin admin = adminService.updateAdminById(id, adminDTO);
        return ResponseEntity.ok(admin);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAdmin(@PathVariable Long id){
        try{
            adminService.deleteAdminById(id);
            return ResponseEntity.ok("The admin with ID " + id + " has been deleted");
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/fetch/all")
    public ResponseEntity<List<Admin>> fetchAll(){
        List<Admin> admins = adminService.findAllAdmins();
        return ResponseEntity.ok(admins);
    }
    @GetMapping("/fetch/{id}")
    public ResponseEntity<Admin> fetchById(@PathVariable long id){
        Admin admin= adminService.findAdminById(id);
        return ResponseEntity.ok(admin);
    }
}
