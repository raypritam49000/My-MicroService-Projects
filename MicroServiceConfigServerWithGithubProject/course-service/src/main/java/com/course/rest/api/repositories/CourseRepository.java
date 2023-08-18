package com.course.rest.api.repositories;

import com.course.rest.api.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
    public Optional<Course> findByStudentId(String studentId);

    public List<Course> findAllByStudentId(String studentId);


}
