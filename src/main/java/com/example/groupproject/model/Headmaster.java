package com.example.groupproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "headmaster")
@Entity
public class Headmaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "headmaster_id")
    long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    User user;

    //json ignore to fix the infinite loop bw headmaster and school
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "school_id")
    School school;

}