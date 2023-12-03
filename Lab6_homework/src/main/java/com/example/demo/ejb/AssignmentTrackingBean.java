package com.example.demo.ejb;

import com.example.demo.model.Project;
import com.example.demo.repository.ProjectRepository;
import lombok.Getter;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Singleton
@Named
public class AssignmentTrackingBean {
    private Map<Long, Long> assignments;

    @EJB
    private ProjectRepository projectRepository;

    @EJB
    private ProjectAssignmentBean projectAssignmentBean;

    @PostConstruct
    public void init() {
        assignments = new HashMap<>();
        List<Project> assignedProjects = projectRepository.findAll();
        for (Project project : assignedProjects) {
            if (project.getStudent() != null) {
                assignments.put(project.getId(), project.getStudent().getId());
            }
        }
    }

    public void addAssignment(Long projectId, Long studentId) {
        assignments.put(projectId, studentId);
    }

    public void removeAssignment(Long projectId) {
        projectAssignmentBean.deleteAssignment(projectId);
        assignments.remove(projectId);
    }

}
