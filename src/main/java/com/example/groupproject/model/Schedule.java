package com.example.groupproject.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "schedule")
@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    long id;

    @NotNull(message = "Day of the week cannot be null")
    @NotBlank (message = "Day of the week cannot be left blank")
    @Size(min = 4, max = 15, message = "Day of the week must be between 4 and 15 digits")
    @Column(name = "day_of_the_week", nullable = false)
    String dayOfTheWeek;

    @NotNull(message = "Number of period cannot be null")
    @NotBlank (message = "Number of period cannot be left blank")
    @Size(max = 1, message = "Grade can only be one digit")
    @Column(name = "number_of_period")
    String numberOfPeriod;

    @ManyToOne
    //@MapsId
    @JoinColumn(name = "term_id")
    @JsonManagedReference
    Term term;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "subject_id")
    Subject subject;

    @ManyToOne
    @JsonManagedReference
    //@MapsId
    @JoinColumn(name = "teacher_id")
    Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "klass_id")
    @JsonManagedReference
    Klass klass;


}

