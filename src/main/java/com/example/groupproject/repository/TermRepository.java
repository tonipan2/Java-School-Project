package com.example.groupproject.repository;
import com.example.groupproject.model.Term;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TermRepository extends JpaRepository<Term, Long> {
        boolean existsById(long termId);

        }