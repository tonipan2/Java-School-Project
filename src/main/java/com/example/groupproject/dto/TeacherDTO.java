package com.example.groupproject.dto;

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
public class TeacherDTO {
    long userId;
    long id;
    Set<Long> schoolIds;
    Set<Long> subjectIds;
}