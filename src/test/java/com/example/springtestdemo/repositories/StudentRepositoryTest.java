package com.example.springtestdemo.repositories;

import com.example.springtestdemo.Gender;
import com.example.springtestdemo.model.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StudentRepositoryTest {

  @Autowired
  private StudentRepository studentRepository;

  @Test
  void itShouldCheckIfExistsEmail() {
    //Given
    Student Anna = new Student();
    Anna.setName("Anna");
    Anna.setEmail("anna@net.com");
    Anna.setGender(Gender.FEMALE);

    studentRepository.save(Anna);

    //When
    boolean expect = studentRepository.selectExistsEmail(Anna.getEmail());

    //Then
    assertThat(expect).isTrue();

  }

  @Test
  void itShouldNotExistEmail() {
    //Given
    String email = "pepito@net.com";

    //When
    boolean expect = studentRepository.selectExistsEmail(email);

    //Then
    assertThat(expect).isFalse();
  }
}