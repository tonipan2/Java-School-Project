package com.example.groupproject.dto;
import com.example.groupproject.model.TermType;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TermDTO {
    long id;
    TermType termType;

}
