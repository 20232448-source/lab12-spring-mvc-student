package vn.edu.eaut.lab12.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import vn.edu.eaut.lab12.model.Student;

@Service
public class StudentService {

    private final List<Student> students = new ArrayList<>(Arrays.asList(
            new Student("SV001", "Nguyen Van An", "anguyen@eaut.edu.vn", "DCCNTT13.10.1", 8.5),
            new Student("SV002", "Nguyen Thi Binh", "binhnt@eaut.edu.vn", "DCCNTT13.10.2", 9.0),
            new Student("SV003", "Le Van Cuong", "cuonglv@eaut.edu.vn", "DCCNTT13.10.3", 7.5),
            new Student("SV004", "Pham Thi Dung", "dungpt@eaut.edu.vn", "DCCNTT13.10.4", 8.0),
            new Student("SV005", "Hoang Van Em", "emhv@eaut.edu.vn", "DCCNTT13.10.5", 7.0)
    ));

    public List<Student> findAll() {
        return students;
    }

    public Student findByCode(String studentCode) {
        return students.stream()
                .filter(student -> student.getStudentCode().equals(studentCode))
                .findFirst()
                .orElse(null);
    }

    public List<Student> searchByName(String keyword) {
        String normalizedKeyword = keyword.trim().toLowerCase();
        return students.stream()
                .filter(student -> student.getFullName().toLowerCase().contains(normalizedKeyword))
                .toList();
    }

    public void save(Student student, String originalStudentCode) {
        Student existing = originalStudentCode == null ? null : findByCode(originalStudentCode);
        if (existing == null) {
            students.add(student);
        } else {
            students.set(students.indexOf(existing), student);
        }
    }

    public void deleteByCode(String studentCode) {
        students.removeIf(student -> student.getStudentCode().equals(studentCode));
    }
}