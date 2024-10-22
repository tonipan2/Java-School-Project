package com.example.groupproject.repository;

import com.example.groupproject.model.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {
    boolean existsById(long schoolId);
   // boolean existsByAddressId(long addressId);
}
