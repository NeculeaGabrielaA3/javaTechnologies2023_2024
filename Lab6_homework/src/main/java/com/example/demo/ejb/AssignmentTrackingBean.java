package com.example.demo.ejb;

import com.example.demo.model.Project;
import com.example.demo.repository.ProjectRepository;
import lombok.Data;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.inject.Named;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
@Named
@Data
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class AssignmentTrackingBean {

    private Map<Long, Long> assignments;

    @EJB
    private ProjectRepository projectRepository;

    @EJB
    private ProjectAssignmentBean projectAssignmentBean;

    @PostConstruct
    public void init() {
        assignments = new HashMap<>();
        initializeAssignments();
    }

    private void initializeAssignments() {
        List<Project> assignedProjects = projectRepository.findAll();
        for (Project project : assignedProjects) {
            if (project.getStudent() != null) {
                assignments.put(project.getId(), project.getStudent().getId());
            }
        }
    }

    @Lock(LockType.WRITE)
    public void addAssignment(Long projectId, Long studentId) {
        assignments.put(projectId, studentId);
    }

    @Lock(LockType.WRITE)
    public void removeAssignment(Long projectId) {
        projectRepository.removeAssignment(projectId);
        assignments.remove(projectId);
    }

}
