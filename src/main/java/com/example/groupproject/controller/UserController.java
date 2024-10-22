package com.example.groupproject.controller;

import com.example.groupproject.dto.UserDTO;
import com.example.groupproject.model.User;
import com.example.groupproject.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody UserDTO userDTO) {
        try {
            userService.saveUser(userDTO);
            return ResponseEntity.ok("User added successfully");
        } catch (Exception e) {
            // Log the exception and return a 500 error response
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<User> patchAccount(@PathVariable Long id, @RequestBody UserDTO userDTO){
        User user = userService.updateUserById(id, userDTO);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id){
        try{
            userService.deleteUserById(id);
            return ResponseEntity.ok("The User has been deleted");
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/fetch/all")
    public ResponseEntity<List<User>> fetchAll(){
        List<User> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/fetch/{id}")
    public ResponseEntity<User> fetchById(@PathVariable long id){
        User user = userService.findUserById(id);
        return ResponseEntity.ok(user);
    }

    // Endpoint to find account by password and username
    @GetMapping("/by-password-username")
    public ResponseEntity<User> getAccountByPasswordAndUsername(
            @RequestParam("password") String password,
            @RequestParam("username") String username) {

        User user = userService.findUserByPasswordAndUsername(password, username);
        return  ResponseEntity.ok(user);
    }


    //add role to an already created user method

}
