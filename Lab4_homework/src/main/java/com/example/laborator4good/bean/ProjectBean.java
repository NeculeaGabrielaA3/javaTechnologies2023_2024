package com.example.laborator4good.bean;


import com.example.laborator4good.model.Project;
import lombok.Data;

import lombok.Data;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Init;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ViewScoped
@ManagedBean(name = "projectBean")
@RequestScoped
@Data
public class ProjectBean implements Serializable {
    private List<Project> projects;
    
    @Resource(name = "jdbc/myDataSource")
    private DataSource dataSource;

    private Long id;
    private String name;
    private String category;
    private String description;
    private Date deadline;

    public List<Project> getAllProjects() {

        List<Project> projects = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {

            String selectQuery = "SELECT * FROM project";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    Project project = new Project();
                    project.setId(resultSet.getInt("id"));
                    project.setName(resultSet.getString("name"));
                    project.setCategory(resultSet.getString("category"));
                    project.setDescription(resultSet.getString("description"));
                    project.setDeadline(resultSet.getDate("deadline"));
                    projects.add(project);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    public void createProject(Project project) {

        try (Connection connection = dataSource.getConnection()) {

            String insertQuery = "INSERT INTO project (name, category, description) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, project.getName());
                preparedStatement.setString(2, project.getCategory());
                preparedStatement.setString(3, project.getDescription());
                //preparedStatement.setDate(4, new java.sql.Date(this.deadline.getTime()));
                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProject(int projectId) {

        try (Connection connection = dataSource.getConnection()) {

            String deleteQuery = "DELETE FROM project WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, projectId);
                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}