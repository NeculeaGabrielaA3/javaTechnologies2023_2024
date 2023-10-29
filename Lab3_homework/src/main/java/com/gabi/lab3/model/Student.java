package com.gabi.lab3.model;

import com.gabi.lab3.database.DatabaseConnector;
import com.gabi.lab3.dto.StudentDTO;
import lombok.Data;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@ManagedBean
@SessionScoped
@Data
public class Student implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

}
