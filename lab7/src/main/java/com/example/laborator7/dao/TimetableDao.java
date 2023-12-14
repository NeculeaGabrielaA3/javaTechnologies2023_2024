package com.example.laborator7.dao;

import com.example.laborator7.entity.Timetable;

import java.util.List;

public interface TimetableDao {
    Timetable create(Timetable t) throws Exception;
    Timetable update(Timetable t);
    Timetable get (Integer id);
    List<Timetable> getAll();
    void delete(Long id);
    Timetable getByProperty(String query, String prop);
}
