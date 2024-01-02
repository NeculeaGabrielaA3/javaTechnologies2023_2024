package com.example.laborator7.controller;

import com.example.laborator7.bean.TimetableBean;
import com.example.laborator7.dao.TimetableDao;
import com.example.laborator7.dao.impl.UserDaoImpl;
import com.example.laborator7.entity.Timetable;
import com.example.laborator7.entity.User;
import com.example.laborator7.interceptor.Logged;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.inject.Instance;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@RequestScoped
@Named
public class TimetableController implements Serializable {

    @Inject
    private UserDaoImpl userDaoImpl;
    @Inject
    private TimetableDao timetableDao;

    @Inject
    private Instance<String> registrationNumberInstance;

    public User getUser(){
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> cookiesMap = externalContext.getRequestCookieMap();

        if (cookiesMap.containsKey("user")) {
            Cookie userCookie = (Cookie) cookiesMap.get("user");
            String username = userCookie.getValue();
            //user = userDaoImpl.findByUsername(username);
            return userDaoImpl.findByUsername(username);
        }
        return null;
    }


    public void create( TimetableBean timetableBean) {

        String registrationNumber = registrationNumberInstance.get();
        timetableBean.setRegistrationNumber(registrationNumber);
        timetableBean.setUser(getUser());

        try {
            timetableDao.create(timetableBean.ConvertToEntity());
        } catch (Exception e) {
            System.out.println("Error while adding timetable: " + e.getMessage());
        }
    }

    public void delete(Long id) {
        try {
            timetableDao.delete(id);

        } catch (Exception e) {
            System.out.println("Error while deleting timetable: " + id + "mesaj:" + e.getMessage());
        }
    }

    public List<Timetable> getAllTimetablePreferences() {
        return timetableDao.getAll();
    }

}
