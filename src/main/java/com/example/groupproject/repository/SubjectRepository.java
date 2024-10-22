package com.example.groupproject.repository;

import com.example.groupproject.model.Student;
import com.example.groupproject.model.Subject;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long>{
    boolean existsById(long packageId);
    //@Query("SELECT s FROM subject s WHERE s.teacher.id = :teacherId")
    //List<Subject> findSubjectsByTeacherId(long teacherId);
}
