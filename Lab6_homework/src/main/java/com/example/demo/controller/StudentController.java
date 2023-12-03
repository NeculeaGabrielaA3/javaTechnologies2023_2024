package com.example.demo.controller;

import com.example.demo.RedirectBean;
import com.example.demo.ejb.ProjectAssignmentBean;
import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import lombok.Data;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import java.util.List;

@Data
@Named
@RequestScoped
public class StudentController {

    @EJB
    private StudentRepository studentRepository;
    @EJB
    private ProjectAssignmentBean projectAssignmentBean;

    private String name;

    private Long selectedStudentId;
    private Long selectedProjectId;

    public String addStudent() {
        Student student = new Student();
        student.setName(name);

        studentRepository.save(student);
        RedirectBean.redirectToViewStudents();
        return "success";
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }
}