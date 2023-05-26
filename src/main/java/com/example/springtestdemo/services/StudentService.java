package com.example.springtestdemo.services;

import com.example.springtestdemo.exception.BadRequestException;
import com.example.springtestdemo.exception.StudentNotFoundException;
import com.example.springtestdemo.model.Student;
import com.example.springtestdemo.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentService {

  private final StudentRepository studentRepository;

  public List<Student> getAllStudents() {
    return studentRepository.findAll();
  }

  public void addStudent(Student student) {
    Boolean existEmail = studentRepository.selectExistsEmail(student.getEmail());
    if (existEmail) {
      throw new BadRequestException("Email %s taken!".formatted(student.getEmail()));
    }
    studentRepository.save(student);
  }

  public void deleteStudent(UUID id) {
    if (!studentRepository.existsById(id)) {
      throw new StudentNotFoundException("Student with %s does not exist!".formatted(id));
    }
    studentRepository.deleteById(id);
  }
}
