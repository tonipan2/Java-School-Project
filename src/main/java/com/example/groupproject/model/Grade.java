package com.example.groupproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "grade")
@Entity
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_id")
    long id;

    @DecimalMin(value = "2.0", message = "Grade must be at least 2.0")
    @DecimalMax(value = "6.0", message = "Grade must be at most 6.0")
    @Column(name = "grade", nullable = false)
    BigDecimal grade;

    @NotNull(message = "Date added cannot be null")
    @Column(name = "date_added", nullable = false)
    LocalDateTime dateAdded;

    @NotNull(message = "Date of grade cannot be null")
    @Column(name = "date_of_grade", nullable = false)
    LocalDate dateOfGrade;

    @Column(name = "type_of_grade", nullable = false)
    GradeType gradeType;

    @NotNull(message = "Student cannot be null")
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    @JsonIgnore
    Student student;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    @JsonManagedReference
    Subject subject;

    @ManyToOne
    @JoinColumn(name = "term_id", nullable = false)
    @JsonManagedReference
    Term term;

    //dobavi data
    //ще те убия с тия коментари - марти

}
