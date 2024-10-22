package com.example.groupproject.repository;

import com.example.groupproject.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    boolean existsById(long gradeId);
}
