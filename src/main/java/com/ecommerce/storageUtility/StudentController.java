package com.ecommerce.storageUtility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.rmi.server.UID;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping("/add")
    public ResponseEntity<Student> createStudent(
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage
    ) throws IOException {
        // Hardcoded student values
        Student staticStudent = new Student();
        staticStudent.setName("John Doe"+ Math.random());
        staticStudent.setAddress("123 Main St"+Math.random());
        staticStudent.setEducation("B.Tech");
        return ResponseEntity.ok(studentService.createStudent(staticStudent, profileImage));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/getBydId/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }
}