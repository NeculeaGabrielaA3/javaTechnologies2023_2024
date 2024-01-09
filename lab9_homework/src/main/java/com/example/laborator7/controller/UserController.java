package com.example.laborator7.controller;

import com.example.laborator7.bean.UserBean;
import com.example.laborator7.dao.impl.GroupDaoImpl;
import com.example.laborator7.dao.impl.UserDaoImpl;
import com.example.laborator7.entity.User;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@RequestScoped
@Named
public class UserController implements Serializable {
    @Inject
    UserDaoImpl userDaoImpl;
    //@Inject
    //UserGroupDaoImpl userGroupDaoImpl;
    @Inject
    GroupDaoImpl groupDaoImpl;

    public UserController() {
    }

    public void register(UserBean userbean)
    {
        try {
            // creating the actual user
            String hashedPassword = hashPassword(userbean.getPassword());
            userbean.setPassword(hashedPassword);
            User user = userDaoImpl.create(userbean.ConvertToEntity());

            //creating the entry in the user_group table
//            UserGroup ug = new UserGroup();
//            Group group;
//            if(!userbean.isAdmin()) {
//                group = groupDaoImpl.findByGroupId("user");
//            }
//            else {
//                group = groupDaoImpl.findByGroupId("admin");
//            }
//            user = userDaoImpl.findByUsername(userbean.getUsername());
//            UserGroupId userGroupId = new UserGroupId(user.getUserId(), group.getId());
//            ug.setId(userGroupId);
//            ug.setUser(user);
//            ug.setGroup(group);
//
//            userGroupDaoImpl.create(ug);

            FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
        } catch (Exception e) {
            addErrorMessage(e);
        }
    }

//    public void login(UserBean userBean)
//    {
//        try {
//            User user = userDaoImpl.login(userBean.ConvertToEntity());
//            setCookie("user", user.getUsername());
//            setCookie("userId", String.valueOf(user.getUserId()));
//            setCookie("isAdmin", String.valueOf(user.isAdmin()));
//            if(user.isAdmin()) {
//                FacesContext.getCurrentInstance().getExternalContext().redirect("viewTimetables.xhtml");
//            }
//            else {
//                FacesContext.getCurrentInstance().getExternalContext().redirect("addTimetable.xhtml");
//            }
//        } catch (Exception e) {
//            addErrorMessage(e);
//        }
//    }

    public void login(UserBean userBean) {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        try {
            System.out.println(userBean.getUsername() + " " + userBean.getPassword());
            request.login(userBean.getUsername(), userBean.getPassword());
            externalContext.log("Login successful: " + userBean.getUsername());
            User user = userDaoImpl.findByUsername(request.getUserPrincipal().getName());

            if(request.isUserInRole("user")) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("../user/addTimetable.xhtml");
            }
            else if(request.isUserInRole("admin")) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("../admin/viewTimetables.xhtml");
            }
        } catch (ServletException | RuntimeException | IOException e) {
            //logger.log(Level.SEVERE, e.getMessage());
            addErrorMessage(e);
        }
    }

    public String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        return bytesToHex(digest);
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public void logout()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest)
                context.getExternalContext().getRequest();
        try {
            request.logout();
            FacesContext.getCurrentInstance().getExternalContext().redirect("../public/login.xhtml");
        } catch (ServletException | IOException e) {
            context.addMessage(null, new FacesMessage("Logout failed."));
            e.printStackTrace();
        }
    }

    private void addErrorMessage(Exception e) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: " + e.getMessage(),"Error: " + e.getMessage());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
