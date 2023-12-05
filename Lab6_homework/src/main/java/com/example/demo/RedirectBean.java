package com.example.demo;

import lombok.Data;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
@Data
public class RedirectBean {

    static public void redirectToViewProjects() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();

        try {
            externalContext.redirect("projectView.xhtml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static public void redirectToViewStudents() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();

        try {
            externalContext.redirect("studentView.xhtml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void redirectToEditStudentPage() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();

        try {
            externalContext.redirect("createStudent.xhtml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void redirectToEditProjectPage() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();

        try {
            externalContext.redirect("createProject.xhtml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void redirectToAssignPage() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();

        try {
            externalContext.redirect("assignProjectPage.xhtml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static public void redirectToViewAssignedProjectsPage() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();

        try {
            externalContext.redirect("projectsAssignmentsView.xhtml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}