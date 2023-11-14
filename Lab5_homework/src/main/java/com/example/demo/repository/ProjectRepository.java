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

    @Transactional
    public void save(Project project) {
        entityManager.persist(project);
    }

    public Project findById(Long id) {
        return entityManager.createNamedQuery("Project.findById", Project.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public List<Project> findAll() {
        return entityManager.createNamedQuery("Project.findAll", Project.class)
                .getResultList();
    }
    @Transactional
    public void deleteById(Long id) {
        entityManager.createNamedQuery("Project.deleteById")
                .setParameter("id", id)
                .executeUpdate();
    }
}
