package com.example.demo.repository;

import com.example.demo.model.Project;
import com.example.demo.model.Student;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Stateless
public class ProjectRepository {
    @PersistenceContext(unitName = "StudentPU")
    private EntityManager entityManager;

    public void save(Project project) {
        entityManager.persist(project);
    }

    public List<Project> findAll() {
        return entityManager.createNamedQuery("Project.findAll", Project.class)
                .getResultList();
    }
    public void deleteById(Long id) {
        Project project = entityManager.find(Project.class, id);
        if(project != null)
            entityManager.remove(project);
    }

    public void removeAssignment(Long projectId) {
        Project project = entityManager.find(Project.class, projectId);
        if (project != null) {
            project.assignStudent(null);
            entityManager.merge(project);
        }
    }

}
