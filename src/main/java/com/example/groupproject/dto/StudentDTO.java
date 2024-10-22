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
public class StudentDTO {
    long id;
    private long userId;
    private long schoolId;
    private long klassId;
    private Set<Long> parentIds; //optional
}