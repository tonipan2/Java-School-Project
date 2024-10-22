package com.example.groupproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import jakarta.validation.constraints.*;

import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "school")
@Entity
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="school_id")
    long id;

    @NotNull(message = "School name cannot be null")
    @NotBlank(message = "School name cannot be left blank")
    @Size(max = 30, message = "School name has to be with up to 30 characters")
    @Pattern(
            regexp = "^[\\p{Lu}][\\p{L}'\\s]*$",
            message = "Name of school should start with a capital letter and contain only Cyrillic or English letters, apostrophes, and spaces."
    )
    @Column(name="school_name", nullable = false)
    String name;



    @NotNull(message = "Address cannot be null")
    @NotBlank(message = "Address cannot be left blank")
    @Size(max = 150, message = "Address has to be with up to 150 characters")
    @Column(name = "address", nullable = false)
    String address;

    @OneToOne(mappedBy = "school")
    @JsonIgnore
    Headmaster headmaster;


    @JsonIgnore
    @ManyToMany(mappedBy = "schools")
    Set<Teacher> teachers;

    @ManyToMany
    @JoinTable(
            name = "schools_have_admins",
            joinColumns = @JoinColumn(name = "school_id"),
            inverseJoinColumns = @JoinColumn(name = "admin_id")
    )
    @JsonIgnore
    Set<Admin> admins;

    @JsonIgnore
    @OneToMany(mappedBy = "school")
    Set<Student> students;

    @JsonIgnore
    @OneToMany(mappedBy = "school")
    Set<Klass> klasses;


}