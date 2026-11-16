package com.example.studentapp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// JpaRepository<Entity type, Primary key type>
// No method bodies are needed: Spring Data JPA creates the implementation at start-up.
// Inherited for free: findAll(), findById(), save(), deleteById(), existsById(), count() ...
@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    // Derived query: Spring builds the SQL from the method NAME.
    // Students whose name contains the given text, ignoring upper/lower case.
    List<Student> findByNameContainingIgnoreCase(String name);

    // Derived query: students with cgpa >= the given value
    List<Student> findByCgpaGreaterThanEqual(Float cgpa);

    // JPQL query written by hand.
    // "Student" is the CLASS name and "s.cgpa" is the FIELD name (not table/column names).
    // :minCgpa is a named parameter filled by @Param("minCgpa").
    @Query("SELECT s FROM Student s WHERE s.cgpa >= :minCgpa ORDER BY s.cgpa DESC")
    List<Student> findTopStudents(@Param("minCgpa") Float minCgpa);
}
