package com.example.groupproject.repository;

import com.example.groupproject.model.Headmaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeadmasterRepository extends JpaRepository<Headmaster, Long> {
    boolean existsById(long principalId);

}
