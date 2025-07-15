package com.ecommerce.storageUtility;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import com.ecommerce.storage.StorageService;
@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StorageService storageService;

    public Student createStudent(Student student, MultipartFile profileImage) throws IOException {
        if (profileImage != null && !profileImage.isEmpty()) {
            student.setProfileImageUrl(storageService.store(profileImage));
        }
        return studentRepository.save(student);
    }
    public List<Student> getAllStudents() {
        return studentRepository.findAll(); // Fetch all students from DB
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
    }
}