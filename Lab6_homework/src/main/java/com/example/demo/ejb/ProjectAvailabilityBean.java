package com.example.demo.ejb;

import com.example.demo.model.Project;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ProjectAvailabilityBean {
    @PersistenceContext(unitName = "StudentPU")
    private EntityManager entityManager;

    public boolean isProjectAvailable(Long projectId) {
        if (projectId == null) {
            return false;
        }
        Project project = entityManager.find(Project.class, projectId);
        return project != null && project.getAssignedStudent() == null;
    }

}
