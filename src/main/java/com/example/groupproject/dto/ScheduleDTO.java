package com.example.groupproject.dto;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScheduleDTO {
    long id;
    String dayOfTheWeek;
    String numberOfPeriod;
    private Long termId;
    private Long teacherId;
    private Long klassId;
    private Long subjectId;
}
