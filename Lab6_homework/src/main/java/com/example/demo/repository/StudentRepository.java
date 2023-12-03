package com.example.demo.repository;

import com.example.demo.model.Project;
import com.example.demo.model.Student;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Stateless
public class StudentRepository {

    @PersistenceContext(unitName = "StudentPU")
    private EntityManager entityManager;


    @Transactional
    public void save(Student student) {
        entityManager.persist(student);
    }

    public Student findById(Long id) {
        return entityManager.createNamedQuery("Student.findById", Student.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public List<Student> findAll() {
        return entityManager.createNamedQuery("Student.findAll", Student.class)
                .getResultList();
    }
    @Transactional
    public void deleteById(Long studentId) {
        Student student = entityManager.find(Student.class, studentId);
        if(student != null) {
            // Find all projects assigned to this student and remove the association
            List<Project> projects = entityManager.createQuery("SELECT p FROM Project p WHERE p.student.id = :studentId", Project.class)
                    .setParameter("studentId", studentId)
                    .getResultList();

            for (Project project : projects) {
                project.setStudent(null); // Remove the student reference
                entityManager.merge(project); // Update the project
            }

            // Now delete the student
            entityManager.remove(student);
        }
    }

    public void setEntityManager(EntityManager em) {
        this.entityManager = em;
    }
}
