package com.example.groupproject.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.groupproject.model.GradeType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GradeDTO {
    long id;
    BigDecimal grade;
    GradeType gradeType;
    LocalDateTime dateAdded;
    LocalDate dateOfGrade;
    long studentId;
    long subjectId;
    long termId;
}
