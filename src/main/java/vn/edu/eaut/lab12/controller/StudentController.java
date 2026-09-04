package vn.edu.eaut.lab12.controller;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.edu.eaut.lab12.model.Student;
import vn.edu.eaut.lab12.service.StudentService;

@Controller
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public String students(@RequestParam(required = false, defaultValue = "") String keyword, Model model) {
        List<Student> students = keyword.isBlank() ? studentService.findAll() : studentService.searchByName(keyword);
        model.addAttribute("students", students);
        model.addAttribute("studentCount", students.size());
        model.addAttribute("keyword", keyword);
        return "students";
    }

    @GetMapping("/students/detail/{studentCode}")
    public String detailStudent(@PathVariable String studentCode, Model model) {
        Student student = studentService.findByCode(studentCode);
        if (student == null) {
            return "redirect:/students";
        }
        model.addAttribute("student", student);
        return "student-detail";
    }

    @GetMapping("/students/new")
    public String newStudent(Model model) {
        model.addAttribute("title", "Thêm sinh viên");
        model.addAttribute("student", new Student());
        return "student-form";
    }

    @GetMapping("/students/edit/{studentCode}")
    public String editStudent(@PathVariable String studentCode, Model model) {
        Student student = studentService.findByCode(studentCode);
        if (student == null) {
            return "redirect:/students";
        }
        model.addAttribute("title", "Cập nhật sinh viên");
        model.addAttribute("student", new Student(student.getStudentCode(), student.getFullName(),
            student.getEmail(), student.getClassName(), student.getScore()));
        model.addAttribute("originalStudentCode", studentCode);
        return "student-form";
    }

    @PostMapping("/students/save")
    public String saveStudent(@Valid @ModelAttribute("student") Student student,
                              BindingResult bindingResult,
                              @RequestParam(required = false) String originalStudentCode,
                              Model model) {
        if (hasDuplicateCode(student, originalStudentCode)) {
            bindingResult.rejectValue("studentCode", "duplicate", "Mã sinh viên đã tồn tại");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", originalStudentCode == null ? "Thêm sinh viên" : "Cập nhật sinh viên");
            model.addAttribute("originalStudentCode", originalStudentCode);
            model.addAttribute("errorMessage", "Không thể lưu. Vui lòng kiểm tra lại thông tin.");
            return "student-form";
        }

        studentService.save(student, originalStudentCode);
        return "redirect:/students";
    }

    @PostMapping("/students/delete")
    public String deleteStudent(@RequestParam String studentCode) {
        studentService.deleteByCode(studentCode);
        return "redirect:/students";
    }

    private boolean hasDuplicateCode(Student student, String originalStudentCode) {
        Student matchingStudent = studentService.findByCode(student.getStudentCode());
        return matchingStudent != null
                && (originalStudentCode == null || !student.getStudentCode().equals(originalStudentCode));
    }
}