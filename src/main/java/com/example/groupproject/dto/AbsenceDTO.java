package com.example.groupproject.dto;

import com.example.groupproject.model.AbsenceState;
import com.example.groupproject.model.AbsenceType;
import com.example.groupproject.model.Student;
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
public class AbsenceDTO {

    long id;
    AbsenceState absenceState;
    AbsenceType absenceType;
    LocalDateTime dateAdded;
    LocalDate dateOfAbsence;
    private long studentId;
}