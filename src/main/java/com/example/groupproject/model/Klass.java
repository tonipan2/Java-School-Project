package com.example.groupproject.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "klass")
@Entity
public class Klass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="class_id")
    long id;

    @NotNull(message = "Class name cannot be null")
    @NotBlank(message = "Class name cannot be left blank")
    @Size(max = 30, message = "Class name has to be with up to 30 characters")
    @Pattern(regexp = "\\d+[A-Z]", message = "String should be in the format '1A', '3B', etc.")
    @Column(name="class_name", nullable = false)
    String name;

    @JsonIgnore
    @OneToMany(mappedBy = "klass")
    @JsonBackReference
    Set<Student> students;

    @NotNull(message = "School cannot be null")
    @ManyToOne
    @JoinColumn(name = "school_id", nullable = false)
    //@JsonBackReference
    School school;

    @OneToMany (mappedBy = "klass")
    @JsonBackReference
    Set<Schedule> schedule;



}