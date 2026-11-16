package com.example.studentapp;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// The service layer holds the business logic.
// Controller -> Service -> Repository -> Database
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    // Constructor injection: Spring passes in the repository bean
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // READ all
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // READ one
    // Optional<Student> is a box that either contains a Student or is empty.
    // It forces the caller to handle the "not found" case instead of getting a null.
    public Optional<Student> getStudentById(Integer id) {
        return studentRepository.findById(id);
    }

    // CREATE
    public Student createStudent(Student student) {
        student.setId(null);                    // ignore any id sent by the client; MySQL generates it
        return studentRepository.save(student); // returns the saved object including its new id
    }

    // UPDATE
    public Optional<Student> updateStudent(Integer id, Student newData) {
        // Find the existing row. If it exists, change its fields and save it.
        return studentRepository.findById(id).map(existing -> {
            existing.setName(newData.getName());
            existing.setCgpa(newData.getCgpa());
            return studentRepository.save(existing);   // id is set, so this is an UPDATE
        });
    }

    // DELETE: returns true if a student was deleted, false if the id did not exist
    public boolean deleteStudent(Integer id) {
        if (!studentRepository.existsById(id)) {
            return false;
        }
        studentRepository.deleteById(id);
        return true;
    }

    // SEARCH by part of the name
    public List<Student> searchByName(String name) {
        return studentRepository.findByNameContainingIgnoreCase(name);
    }

    // Students with cgpa >= minCgpa, best first
    public List<Student> getTopStudents(Float minCgpa) {
        return studentRepository.findTopStudents(minCgpa);
    }
}
