package com.example.groupproject.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "term")
@Entity
public class Term {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "term_id")
    long id;

    @Column(name = "term_type")
    TermType termType;


    @OneToMany (mappedBy = "term")
    @PrimaryKeyJoinColumn
    @JsonBackReference
    Set<Schedule> schedule;

    @OneToMany(mappedBy = "term", cascade = CascadeType.ALL)
    @JsonBackReference
    Set<Grade> grades;



}
