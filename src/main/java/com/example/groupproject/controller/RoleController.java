package com.example.groupproject.controller;

import com.example.groupproject.dto.RoleDTO;
import com.example.groupproject.model.Role;
import com.example.groupproject.service.RoleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    private final RoleService roleService;
    @Autowired
    RoleController(RoleService roleService){
        this.roleService = roleService;
    }
    @PostMapping("/add")
    public ResponseEntity<Role> postRole(@RequestBody RoleDTO roleDTO){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/role/add").toUriString());
        Role role = roleService.saveRole(roleDTO);  // Save once
        return ResponseEntity.created(uri).body(role); // Return the saved role
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<Role> patchRole(@PathVariable Long id, @RequestBody RoleDTO roleDTO){
        Role role = roleService.updateRoleById(id, roleDTO);
        return ResponseEntity.ok(role);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable Long id){
        try{
            roleService.deleteRoleById(id);
            return ResponseEntity.ok("The Role has been deleted");
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/fetch/all")
    public ResponseEntity<List<Role>> fetchAll(){
        List<Role> authorities = roleService.findAllRoles();
        return ResponseEntity.ok(authorities);
    }
    @GetMapping("/fetch/{id}")
    public ResponseEntity<Role> fetchById(@PathVariable long id){
        Role role = roleService.findRoleById(id);
        return ResponseEntity.ok(role);
    }

}
