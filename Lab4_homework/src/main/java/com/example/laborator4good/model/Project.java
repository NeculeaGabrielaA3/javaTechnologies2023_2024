package com.example.laborator4good.model;
import lombok.*;

import javax.annotation.Resource;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ViewScoped;
import javax.sql.DataSource;


@ManagedBean
@ViewScoped
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project implements Serializable {

    private int id;
    private String name;
    private String category;
    private String description;
    private Date deadline;

}
