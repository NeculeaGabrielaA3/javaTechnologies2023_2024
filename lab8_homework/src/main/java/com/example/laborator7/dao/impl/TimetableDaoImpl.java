package com.example.laborator7.dao.impl;

import com.example.laborator7.dao.TimetableDao;
import com.example.laborator7.entity.Timetable;
import com.example.laborator7.interceptor.Logged;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Named
public class TimetableDaoImpl implements TimetableDao {

    public TimetableDaoImpl(){

    }
    @PersistenceContext(name = "StudentPU")
    EntityManager em;

    @Logged
    @Override
    public Timetable create(Timetable timetable) throws Exception {
        if(findTimeslot(timetable.getDayOfWeek(), timetable.getHourOfDay()) != null) {
            throw new Exception("Timeslot not available.");
        }
        em.persist(timetable);
        em.flush();
        em.refresh(timetable);
        return timetable;
    }

    // In TimetableDaoImpl.java

    public List<Timetable> getTimetablesForUser(Integer userId) {
        return em.createQuery("SELECT t FROM Timetable t WHERE t.teacher.id = :userId", Timetable.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public Timetable update(Timetable timetable) {
        return em.merge(timetable);
    }


    @Override
    public Timetable get(Long id) {
        return em.find(Timetable.class, id);
    }


    @Override
    public boolean delete(Long id) {
        Object reference = em.getReference(Timetable.class, id);
        if(reference != null) {
            em.remove(reference);
            return true;
        }
        return false;
    }

    @Override
    public Timetable getByProperty(String query, String prop) {
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Timetable> getAll() {
        return em.createNamedQuery("TimetablePreference.findAll").getResultList();
    }

    @SuppressWarnings("unchecked")
    public Timetable findTimeslot(String dayOfWeek, String hourOfDay) {
        List<Timetable> list = em.createNamedQuery("TimetablePreference.findTimeslot").setParameter(1, dayOfWeek).setParameter(2, hourOfDay).getResultList();
        if(list.isEmpty())
            return null;
        return list.get(0);
    }
}
