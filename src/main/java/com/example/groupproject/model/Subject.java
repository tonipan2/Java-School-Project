package com.example.groupproject.model;
import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "subject")
@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    long id;

    @NotNull(message = "Subject title cannot be null")
    @NotBlank (message = "Subject title cannot be left blank")
    @Size(max = 50, message = "Subject title has to be with up to 50 characters")
    @Column(name = "title", nullable = false)
    String title;

    @ManyToMany
    @JoinTable(name= "qualification",
    joinColumns = {@JoinColumn(name = "subject_id")},
    inverseJoinColumns = {@JoinColumn(name = "teacher_id")})
    @JsonIgnore
    Set<Teacher> teachers;

    @OneToMany(mappedBy = "subject")
    @JsonBackReference
    Set<Schedule> schedules;

    @OneToMany(mappedBy = "subject")
    @JsonBackReference
    Set<Grade> grades;

}
