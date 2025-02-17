package com.shyam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shyam.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    public boolean existsByEmail(String email);

    public Student findByEmail(String email);
}
