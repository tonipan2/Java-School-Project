package com.example.groupproject.dto;

import com.example.groupproject.model.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Collection;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO {
    long id;
    String username;
    String password;
    String email;
    String egn;
    String firstName;
    String lastName;
    String phoneNumber;
    LocalDate birthDate;
    String address;

    boolean accountNonExpired;
    boolean accountNonLocked;
    boolean credentialsNonExpired;
    boolean enabled;


    Collection<Role> authorities;
}