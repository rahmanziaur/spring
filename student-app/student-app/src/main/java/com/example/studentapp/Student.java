package com.example.studentapp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// @Entity: this class is mapped to a database table. Each object is one row.
@Entity
// @Table: the table name. Without it Hibernate would look for a table called "student".
@Table(name = "students")
public class Student {

    // @Id: this field is the primary key
    @Id
    // IDENTITY: the database generates the value using AUTO_INCREMENT
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    // Maps to: name VARCHAR(100) NULL
    // The validation annotations are checked by the application
    // BEFORE the data reaches the database.
    @NotBlank(message = "Name is required")   // not null, not empty, not only spaces
    @Size(max = 100, message = "Name can have at most 100 characters")
    @Column(name = "name", length = 100, nullable = true)
    private String name;

    // Maps to: cgpa FLOAT NULL
    // We use the wrapper type Float (not the primitive float),
    // because Float can hold null and the column allows NULL.
    @DecimalMin(value = "0.0", message = "CGPA cannot be below 0.0")
    @DecimalMax(value = "4.0", message = "CGPA cannot be above 4.0")
    @Column(name = "cgpa", nullable = true)
    private Float cgpa;

    // JPA REQUIRES a no-argument constructor to create objects from rows
    public Student() {
    }

    // Convenience constructor without id, because MySQL generates the id
    public Student(String name, Float cgpa) {
        this.name = name;
        this.cgpa = cgpa;
    }

    // Getters and setters: Spring and Hibernate use them to read and write the fields.
    // In IntelliJ: press Alt + Insert -> "Getter and Setter" to generate them.
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Float getCgpa() { return cgpa; }
    public void setCgpa(Float cgpa) { this.cgpa = cgpa; }
}
