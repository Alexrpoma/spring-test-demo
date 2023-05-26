package com.example.springtestdemo.controller;

import com.example.springtestdemo.model.Student;
import com.example.springtestdemo.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/student")
public class StudentController {

  private final StudentService studentService;

  @GetMapping
  public ResponseEntity<List<Student>> getAllStudents() {
    return ResponseEntity.ok(studentService.getAllStudents());
  }

  @PostMapping
  public ResponseEntity<Void> addStudent(@RequestBody Student student) {
    studentService.addStudent(student);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Void> deleteStudent(@PathVariable(name = "id") UUID id) {
    studentService.deleteStudent(id);
    return ResponseEntity.accepted().build();
  }
}
