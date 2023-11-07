package com.example.laborator4good.model;


import com.example.laborator4good.connection.DatabaseManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ManagedBean
@ViewScoped
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable {

    private Integer id;
    private String name;
}