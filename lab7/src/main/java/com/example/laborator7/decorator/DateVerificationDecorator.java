package com.example.laborator7.decorator;

import com.example.laborator7.dao.TimetableDao;
import com.example.laborator7.entity.Timetable;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import java.time.LocalDateTime;

@Decorator
public abstract class DateVerificationDecorator implements TimetableDao {
    @Inject
    @Delegate
    @Any
    TimetableDao dao;

    @Override
    public Timetable create(Timetable timetable) throws Exception {
        int startHour = 9;
        int finishHour = 12;
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now.getHour());
        if (now.getHour() < startHour || now.getHour() > finishHour)
            throw new Exception("Registration closed. Please register between " + startHour + " and " + finishHour);
        return dao.create(timetable);
    }
}