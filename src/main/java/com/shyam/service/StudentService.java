package com.shyam.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.shyam.dto.CreateStudentRequest;
import com.shyam.entity.Student;
import com.shyam.repository.StudentRepository;

import jakarta.transaction.Transactional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Transactional
    public Student createStudent(CreateStudentRequest studentRequest){
        if (!studentRepository.existsByEmail(studentRequest.getEmail())){
            Student student = new Student();
            BeanUtils.copyProperties(studentRequest, student);

            return studentRepository.save(student);
        }
        
        throw new ResponseStatusException(
            HttpStatus.CONFLICT,
            "Student already exists with same email id"
        );
    }

    public List<Student> readStudents(){
        return studentRepository.findAll();
    }

    @Transactional
    public Student updateStudent(int id, CreateStudentRequest studentRequest){
        Student student = studentRepository
                                .findById(id)
                                .orElseThrow(() ->
                                    new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "User not found with given id"
                                    )
                                );

        BeanUtils.copyProperties(studentRequest, student);
        return studentRepository.save(student);
    }

    @Transactional
    public void deleteStudent(int id){
        Student student = studentRepository
                                .findById(id)
                                .orElseThrow(() ->
                                    new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "User not found with given id"
                                    )
                                );

        studentRepository.delete(student);
    }

}
