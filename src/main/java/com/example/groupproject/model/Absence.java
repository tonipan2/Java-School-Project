package com.example.groupproject.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "absence")
@Entity
public class Absence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "absence_id")
    long id;

    @NotNull(message = "Date added cannot be null")
    @Column(name = "date_added", nullable = false)
    LocalDateTime dateAdded;

    @NotNull(message = "Date of absence cannot be null")
    @Column(name = "date_of_absence", nullable = false)
    LocalDate dateOfAbsence;


    @Column(name = "is_excused", nullable = false)
    AbsenceState absenceState;

    @Column(name = "half_or_full", nullable = false)
    AbsenceType absenceType;

    @NotNull(message = "Student cannot be null")
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    @JsonBackReference
    Student student;

}
