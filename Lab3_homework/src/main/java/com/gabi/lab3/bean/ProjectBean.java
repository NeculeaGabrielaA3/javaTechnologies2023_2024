package com.gabi.lab3.bean;

import com.gabi.lab3.database.DatabaseConnector;
import com.gabi.lab3.dto.ProjectDTO;
import com.gabi.lab3.dto.StudentDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.DualListModel;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SessionScoped
@Data
@Getter
@Setter
@ManagedBean(name = "projectBean")
public class ProjectBean implements Serializable {
    private String projectName;
    private String projectCategory;
    private String projectDescription;
    private Date projectDeadline;
    private DualListModel<StudentDTO> selectedStudents;

    public List<ProjectDTO> getAllProjects() throws ClassNotFoundException {
        Connection connection = DatabaseConnector.connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<ProjectDTO> projects = new ArrayList<>();

        if (connection != null) {
            try {
                String query = "SELECT * FROM project";
                preparedStatement = connection.prepareStatement(query);
                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    ProjectDTO project = new ProjectDTO(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("category"),
                            resultSet.getString("description"),
                            resultSet.getDate("deadline")
                    );
                    projects.add(project);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return projects;
    }

    public List<StudentDTO> allStudents() throws ClassNotFoundException {
        Connection connection = DatabaseConnector.connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<StudentDTO> students = new ArrayList<>();

        if (connection != null) {
            try {
                String query = "SELECT * FROM student";
                preparedStatement = connection.prepareStatement(query);
                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    StudentDTO student = new StudentDTO(
                            resultSet.getInt("id"),
                            resultSet.getString("name")
                    );
                    students.add(student);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return students;
    }

    public void addProject() throws ClassNotFoundException {
        Connection connection = DatabaseConnector.connect();
        PreparedStatement preparedStatement = null;

        if (connection != null) {
            try {
                String query = "INSERT INTO project (name, category, description, deadline) VALUES (?, ?, ?, ?)";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, projectName);
                preparedStatement.setString(2, projectCategory);
                preparedStatement.setString(3, projectDescription);
                preparedStatement.setDate(4, new java.sql.Date(projectDeadline.getTime()));
                preparedStatement.executeUpdate();

                // After saving the project, you can also save the associations with students.
                //saveProjectStudentAssociations(connection);

                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Project added successfully!", null)
                );
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (preparedStatement != null) {
                        preparedStatement.close();
                    }
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void deleteProject(int projectId) {
        try {
            Connection connection = DatabaseConnector.connect();

            if (connection != null) {
                String deleteProjectQuery = "DELETE FROM project WHERE id = ?";
                String deleteProjectStudentQuery = "DELETE FROM project_student WHERE project_id = ?";

                connection.setAutoCommit(false);

                try (PreparedStatement deleteProjectStudentStatement = connection.prepareStatement(deleteProjectStudentQuery);
                     PreparedStatement deleteProjectStatement = connection.prepareStatement(deleteProjectQuery)) {

                    deleteProjectStudentStatement.setInt(1, projectId);
                    deleteProjectStudentStatement.executeUpdate();

                    deleteProjectStatement.setInt(1, projectId);
                    deleteProjectStatement.executeUpdate();

                    connection.commit();
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    private int getLatestProjectId(Connection connection) throws SQLException {
        String query = "SELECT MAX(id) AS latest_id FROM project";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt("latest_id");
    }
}
