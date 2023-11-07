package com.example.laborator4good.connection;

import com.example.laborator4good.model.Student;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.RequestScoped;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.naming.Context;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Named
@ApplicationScoped
public class DatabaseManager {

    @Resource(name = "jdbc/myDataSource")
    private DataSource dataSource;

    public void executeDatabaseOperation() {
        try {
            Context ctx = new InitialContext();
            DataSource dataSource = (DataSource) ctx.lookup("jdbc/myDataSource");
            try (Connection connection = dataSource.getConnection()) {

            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
                String selectQuery = "SELECT * FROM student";
                try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                     ResultSet resultSet = preparedStatement.executeQuery()) {

                    while (resultSet.next()) {
                        Student student = new Student();
                        student.setId(resultSet.getInt("id"));
                        student.setName(resultSet.getString("name"));
                        students.add(student);

                    }
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }

    public void deleteStudent(Long student_id) {
        try (Connection connection = dataSource.getConnection()) {
            String deleteQuery = "DELETE FROM student WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setLong(1, student_id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String insertStudent(String name) {
        try (Connection connection = dataSource.getConnection()) {
            String insertQuery = "INSERT INTO student (name) VALUES (?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, name);
                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    return "success";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
