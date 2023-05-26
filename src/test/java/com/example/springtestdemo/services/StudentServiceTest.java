package com.example.springtestdemo.services;

import com.example.springtestdemo.Gender;
import com.example.springtestdemo.exception.BadRequestException;
import com.example.springtestdemo.exception.StudentNotFoundException;
import com.example.springtestdemo.model.Student;
import com.example.springtestdemo.repositories.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class StudentServiceTest {

  @Mock
  private StudentRepository studentRepository;
  private StudentService studentServiceUnderTest;
  private AutoCloseable autoCloseable;

  @BeforeEach
  void setUp() {
    autoCloseable = MockitoAnnotations.openMocks(this);
    studentServiceUnderTest = new StudentService(studentRepository);
  }

  @AfterEach
  void tearDown() throws Exception {
    autoCloseable.close();
  }

  @Test
  void getAllStudents() {
    //When
    studentServiceUnderTest.getAllStudents();

    //Then
    verify(studentRepository).findAll();
  }

  @Test
  void addStudent() {
    //Given
    Student john = new Student();
    john.setName("John");
    john.setEmail("john@net.com");
    john.setGender(Gender.MALE);

    //When
    studentServiceUnderTest.addStudent(john);
    ArgumentCaptor<Student> argumentCaptor = ArgumentCaptor.forClass(Student.class);

    //Then
    verify(studentRepository).save(argumentCaptor.capture());

    Student student = argumentCaptor.getValue();
    assertThat(student).isEqualTo(john);
  }

  @Test
  void itShouldThrowBadRequestException() {
    //Given
    Student john = new Student();
    john.setName("John");
    john.setEmail("john@net.com");
    john.setGender(Gender.MALE);

    given(studentRepository.selectExistsEmail(john.getEmail())).willReturn(true);

    //Then
    assertThatThrownBy(() -> studentServiceUnderTest.addStudent(john))
      .isInstanceOf(BadRequestException.class)
      .hasMessageContaining("Email %s taken!".formatted(john.getEmail()));

    verify(studentRepository, never()).save(any());
  }

  @Test
  void deleteStudent() {
    //Given
    UUID id = UUID.randomUUID();
    given(studentRepository.existsById(id)).willReturn(true);

    //When
    studentServiceUnderTest.deleteStudent(id);

    ArgumentCaptor<UUID> idArgCaptor = ArgumentCaptor.forClass(UUID.class);

    //Then
    verify(studentRepository).deleteById(idArgCaptor.capture());
    UUID idCapture = idArgCaptor.getValue();

    assertThat(idCapture).isEqualTo(id);
  }

  @Test
  void itShouldThrownStudentNotExistException() {
    UUID id = UUID.randomUUID();
    given(studentRepository.existsById(id)).willReturn(false);

    assertThatThrownBy(() -> studentServiceUnderTest.deleteStudent(id))
      .isInstanceOf(StudentNotFoundException.class)
      .hasMessageContaining("Student with %s does not exist!".formatted(id));

    verify(studentRepository, never()).deleteById(id);
  }
}