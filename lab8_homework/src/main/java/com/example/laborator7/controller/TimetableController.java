package com.example.laborator7.controller;

import com.example.laborator7.bean.TimetableBean;
import com.example.laborator7.dao.TimetableDao;
import com.example.laborator7.dao.impl.UserDaoImpl;
import com.example.laborator7.entity.Timetable;
import com.example.laborator7.entity.User;

import javax.enterprise.inject.Instance;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.servlet.http.Cookie;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Filter;

@ManagedBean(name = "timetableController")
@RequestScoped
public class TimetableController implements Serializable {

    @Inject
    private UserDaoImpl userDaoImpl;
    @Inject
    private TimetableDao timetableDao;
    private final Client client;
    public TimetableController() {
        client = ClientBuilder.newClient().register(Filter.class);;
    }

    @Inject
    private Instance<String> registrationNumberInstance;

    public User getUser(){
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> cookiesMap = externalContext.getRequestCookieMap();

        if (cookiesMap.containsKey("user")) {
            Cookie userCookie = (Cookie) cookiesMap.get("user");
            String username = userCookie.getValue();
            return userDaoImpl.findByUsername(username);
        }
        return null;
    }


    public void create( TimetableBean timetableBean) {

        String registrationNumber = registrationNumberInstance.get();
        timetableBean.setRegistrationNumber(registrationNumber);
        timetableBean.setTeacher(getUser());

        String timetableUri = "http://localhost:8080/Laborator7-1.0-SNAPSHOT/resources/timetables";
        client.target(timetableUri)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(timetableBean.ConvertToEntity(), MediaType.APPLICATION_JSON));
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", timetableBean.getContent() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void delete(Long id) {
        try {
            Response response = client.target("http://localhost:8080/Laborator7-1.0-SNAPSHOT/resources/timetables")
                    .path("/{id}")
                    .resolveTemplate("id", id)
                    .request()
                    .delete();

            if (response.getStatusInfo().equals(Response.Status.NO_CONTENT)) {
                System.out.println("Timetable with ID " + id + " was successfully deleted.");
            } else if (response.getStatusInfo().equals(Response.Status.NOT_FOUND)) {
                System.out.println("Timetable with ID " + id + " not found.");
            }

        } catch (Exception e) {
            System.out.println("Error while deleting timetable: " + id + " message: " + e.getMessage());
        }
    }

    public List<Timetable> getAllTimetablePreferences() {
        return timetableDao.getAll();
    }

}
