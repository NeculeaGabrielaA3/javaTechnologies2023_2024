package com.example.demo.ejb;

import com.example.demo.model.Project;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ProjectAvailabilityBean {
    @PersistenceContext(unitName = "StudentPU")
    private EntityManager entityManager;

    public boolean isProjectAvailable(Project project) {
        if (project.getId() == null) {
            return false;
        }
        Project resultProject = entityManager.find(Project.class, project.getId());
        return resultProject != null && project.getAssignedStudent() == null;
    }

}
