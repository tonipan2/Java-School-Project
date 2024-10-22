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

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "student")
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    User user;

    @ManyToMany(mappedBy = "students", fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<Parent> parents;

    @NotNull(message = "Class cannot be null")
    @ManyToOne
    @JoinColumn (name = "school_id", nullable = false)
    @JsonBackReference
    School school;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    @JsonManagedReference
    Set<Absence> absences;

    @JsonIgnore
    @OneToMany(mappedBy = "student")
    Set<Grade> grades;

    @NotNull(message = "Klass cannot be null")
    @ManyToOne
    @JoinColumn(name = "klass_id", nullable = false)
    @JsonManagedReference
    Klass klass;


}