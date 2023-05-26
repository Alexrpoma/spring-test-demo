package com.example.springtestdemo.model;

import com.example.springtestdemo.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

import static jakarta.persistence.GenerationType.AUTO;

@Entity
@Getter
@Setter
public class Student {
  @Id
  @GeneratedValue(strategy = AUTO)
  private UUID id;
  @NotBlank
  @Column(nullable = false)
  private String name;
  @NotBlank
  @Column(nullable = false, unique = true)
  private String email;
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Gender gender;
}
