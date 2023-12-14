package com.example.laborator7.controller;

import com.example.laborator7.bean.UserBean;
import com.example.laborator7.dao.impl.UserDaoImpl;
import com.example.laborator7.entity.User;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@RequestScoped
@Named
public class UserController implements Serializable {
    @Inject
    UserDaoImpl userDaoImpl;
    private final Logger logger = Logger.getLogger(getClass().getName());

    public UserController() {
    }

    public void register(UserBean userbean)
    {
        try {
            userDaoImpl.create(userbean.ConvertToEntity());
            FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error while registering the user");
            addErrorMessage(e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            addErrorMessage(e);
        }
    }


    public void login(UserBean userBean)
    {
        try {
            User user = userDaoImpl.login(userBean.ConvertToEntity());
            setCookie("user", user.getUsername());
            setCookie("userId", String.valueOf(user.getUserId()));
            setCookie("isAdmin", String.valueOf(user.isAdmin()));
            if(user.isAdmin()) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("viewTimetables.xhtml");
            }
            else {
                FacesContext.getCurrentInstance().getExternalContext().redirect("addTimetable.xhtml");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            addErrorMessage(e);
        }
    }

    public void logout()
    {
        removeCookie("user");
        removeCookie("isAdmin");
        removeCookie("userId");
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setCookie(String cookieName, String cookieValue) throws UnsupportedEncodingException {
        Map<String, Object> properties = new HashMap<>();
        properties.put("maxAge", 31536000);
        properties.put("path", "/");
        FacesContext.getCurrentInstance().getExternalContext().addResponseCookie(cookieName, URLEncoder.encode(cookieValue, StandardCharsets.UTF_8), properties);
    }

    private void removeCookie(String cookieName){
        Cookie cookie = (Cookie) FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap().get(cookieName);
        cookie.setValue("");
        Map<String, Object> properties = new HashMap<>();
        properties.put("maxAge", 0);
        properties.put("path", "/");
        FacesContext.getCurrentInstance().getExternalContext().addResponseCookie(cookie.getName(), cookie.getValue(), properties);
    }

    private void addErrorMessage(Exception e) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: " + e.getMessage(),"Error: " + e.getMessage());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
