package com.example.demo.controller;

import com.example.demo.RedirectBean;
import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import lombok.Data;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.List;

@Data
@ManagedBean
@RequestScoped
public class StudentController {

    @EJB
    private StudentRepository studentEJB;

    private String name;
    private int age;

    public String addStudent() {
        Student student = new Student();
        student.setName(name);

        studentEJB.save(student);
        RedirectBean.redirectToViewStudents();
        return "success";
    }

    public List<Student> getAllStudents() {
        return studentEJB.findAll();
    }

    public void deleteById(Long id) {
        studentEJB.deleteById(id);
    }
}