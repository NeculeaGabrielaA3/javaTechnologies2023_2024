package com.example.demo.repository;

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
    public void deleteById(Long id) {
        entityManager.createNamedQuery("Student.deleteById")
                .setParameter("id", id)
                .executeUpdate();
    }

    public void setEntityManager(EntityManager em) {
        this.entityManager = em;
    }
}
