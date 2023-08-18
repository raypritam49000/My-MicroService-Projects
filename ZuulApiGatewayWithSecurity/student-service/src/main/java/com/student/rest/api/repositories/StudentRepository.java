package com.student.rest.api.repositories;

import com.student.rest.api.entites.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,String> {
   public Optional<Student> findByName(String name);
}
