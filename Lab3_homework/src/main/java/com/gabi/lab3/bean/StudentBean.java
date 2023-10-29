package com.gabi.lab3.bean;

import com.gabi.lab3.database.DatabaseConnector;
import com.gabi.lab3.dto.ProjectDTO;
import com.gabi.lab3.dto.StudentDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.el.MethodExpression;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Data
@SessionScoped
@Getter
@Setter
@ManagedBean(name = "studentBean")
public class StudentBean implements Serializable {
    String name;

    static int selectedStudentId = 1;
    public String addStudent() throws ClassNotFoundException {
        Connection connection = DatabaseConnector.connect();
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                String sqlQuery = "INSERT INTO student (name) VALUES ('" + this.name + "');";
                int resultSet = statement.executeUpdate(sqlQuery);
                statement.close();
                connection.close();

                FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Student added successfully!"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public List<StudentDTO> getAllStudents() throws ClassNotFoundException{
        Connection connection = DatabaseConnector.connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<StudentDTO> students = new ArrayList<>();

        if(connection != null) {
            try {
                String q = "SELECT * FROM student";
                preparedStatement = connection.prepareStatement(q);
                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    StudentDTO dto = new StudentDTO(resultSet.getInt("id"), resultSet.getString("name"));
                    students.add(dto);
                }
            }
            catch(SQLException e) {
                e.printStackTrace();
            }
        }
        return students;
    }

    public void deleteStudent(int studentId) throws ClassNotFoundException {
        String deleteQuery = "DELETE FROM student WHERE id = ?";
        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            preparedStatement.setInt(1, studentId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User with ID " + studentId + " deleted successfully.");
            } else {
                System.out.println("User with ID " + studentId + " not found or not deleted.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void showAssociatedProjects(int studentId) throws ClassNotFoundException {
//        Connection connection = DatabaseConnector.connect();
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//        List<ProjectDTO> projects = new ArrayList<>();
        this.selectedStudentId = studentId;

//        if (connection != null) {
//            try {
//                String query = "SELECT p.* FROM project p JOIN project_student ps ON p.id = ps.project_id WHERE ps.student_id = ?";
//                preparedStatement = connection.prepareStatement(query);
//                preparedStatement.setInt(1, studentId);
//                resultSet = preparedStatement.executeQuery();
//
//                while (resultSet.next()) {
//                    ProjectDTO project = new ProjectDTO(
//                            resultSet.getInt("id"),
//                            resultSet.getString("name"),
//                            resultSet.getString("category"),
//                            resultSet.getString("description"),
//                            resultSet.getDate("deadline")
//                    );
//                    projects.add(project);
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return projects;
    }

    public List<ProjectDTO> getAssociatedProjects() throws ClassNotFoundException {
        Connection connection = DatabaseConnector.connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<ProjectDTO> projects = new ArrayList<>();

        if (connection != null) {
            try {
                String query = "SELECT p.* FROM project p JOIN project_student ps ON p.id = ps.project_id WHERE ps.student_id = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, this.selectedStudentId);
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

    public void addProjectToStudent(String projectName) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Establish a database connection
            connection = DatabaseConnector.connect();

            if (connection != null) {
                // First, retrieve the project ID based on the project name
                String selectProjectIdQuery = "SELECT id FROM project WHERE name = ?";
                preparedStatement = connection.prepareStatement(selectProjectIdQuery);
                preparedStatement.setString(1, projectName);
                // Execute the query
                preparedStatement.executeQuery();

                int projectId = -1;

                if (preparedStatement.getResultSet().next()) {
                    projectId = preparedStatement.getResultSet().getInt(1);

                    // Check if the project is not already associated with the student
                    String checkAssociationQuery = "SELECT COUNT(*) FROM project_student WHERE student_id = ? AND project_id = ?";
                    preparedStatement = connection.prepareStatement(checkAssociationQuery);
                    preparedStatement.setInt(1, selectedStudentId);
                    preparedStatement.setInt(2, projectId);

                    // Execute the query
                    preparedStatement.executeQuery();

                    if (preparedStatement.getResultSet().next() && preparedStatement.getResultSet().getInt(1) == 0) {
                        // The project is not associated with the student; add the association
                        String insertAssociationQuery = "INSERT INTO project_student (project_id, student_id) VALUES (?, ?)";
                        preparedStatement = connection.prepareStatement(insertAssociationQuery);
                        preparedStatement.setInt(1, projectId);
                        preparedStatement.setInt(2, selectedStudentId);

                        // Execute the query to add the association
                        int rowsAffected = preparedStatement.executeUpdate();

                        if (rowsAffected > 0) {
                            // The project was successfully associated with the student
                            System.out.println("Project associated with student successfully.");
                        } else {
                            // Handle the case when the association was not added
                            System.out.println("Failed to associate the project with the student.");
                        }
                    } else {
                        // The project is already associated with the student
                        System.out.println("Project is already associated with the student.");
                    }
                } else {
                    // The project with the provided name does not exist
                    System.out.println("Project with the specified name not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

//    public void setSelectedStudent(int id) {
//        selectedStudentId = id;
//    }

    public int getSelectedStudentId() {
        return selectedStudentId;
    }
}
