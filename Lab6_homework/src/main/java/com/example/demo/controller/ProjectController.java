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
    private Long selectedStudentId;

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

        projectRepository.save(project);

        RedirectBean.redirectToViewProjects();

        return "success";
    }
    public boolean isProjectAvailable(Long projectId) {
        return projectAvailabilityBean.isProjectAvailable(projectId);
    }

    public void assignProjectToStudent() {
        if (isProjectAvailable(selectedProjectId)) {
            projectAssignmentBean.assignProjectToStudent(selectedProjectId, selectedStudentId);
            assignmentTrackingBean.addAssignment(selectedProjectId, selectedStudentId);
        }
    }

    public List<Project> getAssignedProjects(){
        List<Project> result = new ArrayList<>();
        List<Project> assignedProjects = projectRepository.findAll();
        for (Project project : assignedProjects) {
            if (project.getStudent() != null) {
                result.add(project);
            }
        }
        return result;
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public void deleteById(Long id) {
        projectRepository.deleteById(id);
    }
}
