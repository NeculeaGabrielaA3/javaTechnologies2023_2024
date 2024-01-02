package com.example.laborator7.dao.impl;

import com.example.laborator7.dao.GenericDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class GenericDaoImpl<T> implements GenericDao<T> {

    @PersistenceContext(name = "StudentPU")
    EntityManager em;

    protected Class<T> entityClass;

    public GenericDaoImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public T create(T t) throws Exception {
        em.persist(t);
        em.flush();
        em.refresh(t);
        return t;
    }

    public T update(T t) {
        return em.merge(t);
    }

    public T get(Integer id) {
        if (id == null) {
            return null;
        }
        return em.find(entityClass, id);
    }

    @SuppressWarnings("unchecked")
    public List<T> getAll(String query) {

        return em.createNamedQuery(query).getResultList();
    }

    @Override
    public void delete(Integer id) {

    }

    @SuppressWarnings("unchecked")
    public T getByProperty(String query, String prop) {

        List<T> list = em.createNamedQuery(query).setParameter(1, prop).getResultList();
        if (list.isEmpty())
            return null;
        return list.get(0);
    }

    @SuppressWarnings("unchecked")
    public T getByTwoProperties(String query, String p1, String p2) {
        List<T> list = em.createNamedQuery(query).setParameter(1, p1).setParameter(2, p2).getResultList();
        if(list.isEmpty())
            return null;
        return list.get(0);
    }

    public void delete(Long id) {
        Object reference = em.getReference(entityClass, id);
        em.remove(reference);
    }
}

