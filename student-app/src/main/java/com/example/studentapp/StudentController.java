package com.example.studentapp;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
// Every URL in this class starts with /api/students
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // ---------- READ ALL ----------
    // GET /api/students  ->  200 OK + JSON array
    @GetMapping
    public List<Student> getAll() {
        return studentService.getAllStudents();
    }

    // ---------- READ ONE ----------
    // GET /api/students/1  ->  200 OK + student, or 404 Not Found
    @GetMapping("/{id}")
    public ResponseEntity<Student> getById(@PathVariable Integer id) {
        return studentService.getStudentById(id)
                .map(student -> ResponseEntity.ok(student))        // found: 200
                .orElse(ResponseEntity.notFound().build());        // empty: 404
    }

    // ---------- CREATE ----------
    // POST /api/students  ->  201 Created + the saved student
    // @RequestBody converts the JSON body into a Student object
    // @Valid checks the validation rules written on the Student fields
    @PostMapping
    public ResponseEntity<Student> create(@Valid @RequestBody Student student) {
        Student saved = studentService.createStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // ---------- UPDATE ----------
    // PUT /api/students/1  ->  200 OK + the updated student, or 404 Not Found
    @PutMapping("/{id}")
    public ResponseEntity<Student> update(@PathVariable Integer id,
                                          @Valid @RequestBody Student student) {
        return studentService.updateStudent(id, student)
                .map(updated -> ResponseEntity.ok(updated))
                .orElse(ResponseEntity.notFound().build());
    }

    // ---------- DELETE ----------
    // DELETE /api/students/1  ->  204 No Content, or 404 Not Found
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (studentService.deleteStudent(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // ---------- SEARCH ----------
    // GET /api/students/search?name=ra
    // @RequestParam reads the value after the ? in the URL
    @GetMapping("/search")
    public List<Student> search(@RequestParam String name) {
        return studentService.searchByName(name);
    }

    // ---------- TOP STUDENTS ----------
    // GET /api/students/top?minCgpa=3.5
    // defaultValue is used when the client does not send the parameter
    @GetMapping("/top")
    public List<Student> top(@RequestParam(defaultValue = "3.5") Float minCgpa) {
        return studentService.getTopStudents(minCgpa);
    }
}
