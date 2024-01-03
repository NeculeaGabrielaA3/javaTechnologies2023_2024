package com.example.laborator7.dao;

import com.example.laborator7.entity.Timetable;

import java.util.List;

public interface TimetableDao {
    Timetable create(Timetable t) throws Exception;
    Timetable update(Timetable t);
    Timetable get (Long id);
    List<Timetable> getAll();
    boolean delete(Long id);
    Timetable getByProperty(String query, String prop);
}
