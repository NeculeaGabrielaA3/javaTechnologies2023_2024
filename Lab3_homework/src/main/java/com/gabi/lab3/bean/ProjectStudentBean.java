package com.gabi.lab3.bean;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;

@SessionScoped
@Data
@Getter
@Setter
@ManagedBean(name = "projectStudentBean")
public class ProjectStudentBean implements Serializable {
}
