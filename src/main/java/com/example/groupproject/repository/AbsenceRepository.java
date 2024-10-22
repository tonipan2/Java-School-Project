package com.example.groupproject.repository;

import com.example.groupproject.model.Absence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Long> {
    boolean existsById(long accountId);
}
