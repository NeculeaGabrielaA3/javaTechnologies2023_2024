package com.example.laborator7.bean;

import lombok.Data;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import java.io.IOException;
import java.util.Map;

@ManagedBean
@ViewScoped
@Data
public class RedirectBean {

    public void redirectToPage() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();

        Map<String, Object> cookiesMap = externalContext.getRequestCookieMap();

        if (cookiesMap.containsKey("isAdmin")) {
            Cookie isAdminCookie = (Cookie) cookiesMap.get("isAdmin");
            String isAdminValue = isAdminCookie.getValue();

            if ("true".equals(isAdminValue)) {
                externalContext.redirect("adminPage.xhtml");
            } else {
                externalContext.redirect("userPage.xhtml");
            }

        } else {
            externalContext.redirect("home.xhtml");
        }
    }
}

