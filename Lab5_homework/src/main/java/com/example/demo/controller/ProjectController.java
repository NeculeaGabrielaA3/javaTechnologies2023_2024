package com.example.demo.controller;

import com.example.demo.RedirectBean;
import com.example.demo.model.Project;
import com.example.demo.repository.ProjectRepository;
import lombok.Data;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.Date;
import java.util.List;

@Data
@ManagedBean
@RequestScoped
public class ProjectController {

    @EJB
    private ProjectRepository projectEJB;

    private String name;
    private String category;
    private String description;
    private Date deadline;

    public String addProject() {
        Project project = new Project();
        project.setName(name);
        project.setCategory(category);
        project.setDeadline(deadline);
        project.setDescription(description);

        projectEJB.save(project);

        RedirectBean.redirectToViewProjects();

        return "success";
    }

    public List<Project> getAllProjects() {
        return projectEJB.findAll();
    }

    public void deleteById(Long id) {
        projectEJB.deleteById(id);
    }
}
