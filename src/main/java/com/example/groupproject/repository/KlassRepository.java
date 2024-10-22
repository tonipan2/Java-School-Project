package com.example.groupproject.repository;

import com.example.groupproject.model.Klass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KlassRepository extends JpaRepository<Klass, Long> {
    boolean existsById(long klassId);

}
