package com.example.groupproject.repository;

import com.example.groupproject.model.Admin;
import com.example.groupproject.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    boolean existsById(long teacherId);

}
