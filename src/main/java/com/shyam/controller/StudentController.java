package com.shyam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shyam.dto.CreateStudentRequest;
import com.shyam.entity.Student;
import com.shyam.service.StudentService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("health")
    public String info(){
        return "The application is up...";
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createStudent(@RequestBody CreateStudentRequest student){
        Student newStudent = studentService.createStudent(student);
        return ResponseEntity
                .status(HttpStatus.CREATED.value())
                .body(Map.of(
                    "message", "Student created successfully",
                    "student", newStudent
                ));
    }

    @GetMapping
    public ResponseEntity<List<Student>> readStudents(){
        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(studentService.readStudents());
    }

    @PutMapping("/{student-id}")
    public ResponseEntity<Map<String, Object>> updateStudet(
        @PathVariable("student-id") int studentId,
        @RequestBody CreateStudentRequest studentRequest
    ){
        Student updateStudent = studentService.updateStudent(studentId, studentRequest);
        
        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(Map.of(
                    "message", "Student record updated successfully",
                    "student", updateStudent
                ));
    }

    @DeleteMapping("/{student-id}")
    public ResponseEntity<Map<String, Object>> deleteStudent(
        @PathVariable("student-id") int studentId
    ){
        studentService.deleteStudent(studentId);
        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(Map.of(
                    "message", "Student record deleted successfully"
                ));
    }
}
