package com.example.groupproject.repository;

import com.example.groupproject.model.Admin;
import com.example.groupproject.model.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {
    boolean existsById(long parentId);
    

}
