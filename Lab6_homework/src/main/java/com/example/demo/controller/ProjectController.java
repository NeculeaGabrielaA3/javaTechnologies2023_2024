package com.example.demo.controller;

import com.example.demo.RedirectBean;
import com.example.demo.ejb.AssignmentTrackingBean;
import com.example.demo.ejb.ProjectAssignmentBean;
import com.example.demo.ejb.ProjectAvailabilityBean;
import com.example.demo.model.Project;
import com.example.demo.repository.ProjectRepository;
import lombok.Data;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Named
@RequestScoped
public class ProjectController {

    @EJB
    private ProjectRepository projectRepository;

    @EJB
    private ProjectAssignmentBean projectAssignmentBean;

    @EJB
    private ProjectAvailabilityBean projectAvailabilityBean;

    @EJB
    private AssignmentTrackingBean assignmentTrackingBean;

    private Long selectedProjectId;

    private String name;
    private String category;
    private String description;
    private Date deadline;

    private List<Long> selectedProjectIds;
    private Long selectedStudentId;

    public void assignProjectsToStudent() {
        projectAssignmentBean.setStudentId(selectedStudentId);
        projectAssignmentBean.setProjectsId(selectedProjectIds);
        projectAssignmentBean.save();
    }

    public String addProject() {
        Project project = new Project();
        project.setName(name);
        project.setCategory(category);
        project.setDeadline(deadline);
        project.setDescription(description);

        projectRepository.save(project);
        RedirectBean.redirectToViewProjects();

        return "success";
    }

    public List<Project> getUnassignedProject() {
        return projectRepository.findAll().stream()
                .filter(projectAvailabilityBean::isProjectAvailable)
                .collect(Collectors.toList());
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public void deleteById(Long id) {
        projectRepository.deleteById(id);
    }
}
