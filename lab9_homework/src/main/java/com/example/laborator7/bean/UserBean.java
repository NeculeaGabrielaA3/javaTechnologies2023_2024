package com.example.laborator7.bean;

import com.example.laborator7.entity.User;
import lombok.Getter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@ManagedBean(name = "userBean")
@RequestScoped
public class UserBean {

    @NotNull
    @Size(min = 1, max = 50)
    private String username;
    @Size(min = 4, message = "The password should have at least 4 characters")
    private String password;
    private boolean admin;
    public UserBean() {
    }

    public UserBean(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.admin = isAdmin;
    }

    public User ConvertToEntity() {
        return new User(this.username, this.password, this.admin);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "username='" + username + '\'' +
                ", pass='" + password + '\'' +
                ", admin=" + admin +
                '}';
    }
}