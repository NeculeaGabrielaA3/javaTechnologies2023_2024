package com.gabi.lab3.service;

import com.gabi.lab3.database.DatabaseConnector;
import com.gabi.lab3.model.Student;
import lombok.Data;


import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@ManagedBean(name = "studentservice")
@SessionScoped
@Data
public class StudentService implements Serializable {

    Connection connection;

    public StudentService() throws ClassNotFoundException {
        connection = DatabaseConnector.connect();
    }

    public String addStudent(String name) throws ClassNotFoundException {
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                String sqlQuery = "INSERT INTO student (name) VALUES ('" + name + "');";
                int resultSet = statement.executeUpdate(sqlQuery);
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
