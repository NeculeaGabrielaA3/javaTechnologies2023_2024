package com.example.laborator4good.bean;

import com.example.laborator4good.model.Project;
import com.example.laborator4good.model.Student;
import lombok.Data;
import org.primefaces.event.RowEditEvent;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ViewScoped
@ManagedBean(name = "studentBean")
@RequestScoped
@Data
public class StudentBean implements Serializable {
     private List<Student> students;
    @Resource(name = "jdbc/myDataSource")
    private DataSource dataSource;
    private String name;
    private String category;
    private int selectedStudentId;
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

    public void deleteStudent(int studentId) throws ClassNotFoundException {
        String deleteProjectStudentQuery = "DELETE FROM project_student WHERE student_id = ?";
        String deleteStudentQuery = "DELETE FROM student WHERE id = ?";

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement psDeleteProjectStudent = connection.prepareStatement(deleteProjectStudentQuery)) {
                psDeleteProjectStudent.setInt(1, studentId);
                psDeleteProjectStudent.executeUpdate();
            }

            try (PreparedStatement psDeleteStudent = connection.prepareStatement(deleteStudentQuery)) {
                psDeleteStudent.setInt(1, studentId);
                psDeleteStudent.executeUpdate();

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void rowCancel(RowEditEvent<Student> event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", String.valueOf(event.getObject().getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void updateRow(RowEditEvent<Student> event) {
        Student editedStudent = event.getObject();
        String updateNameQuery = "UPDATE student SET name = ? WHERE id = ?";

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement psDeleteProjectStudent = connection.prepareStatement(updateNameQuery)) {
                psDeleteProjectStudent.setString(1, editedStudent.getName());
                psDeleteProjectStudent.setInt(2, editedStudent.getId());
                psDeleteProjectStudent.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        FacesMessage msg = new FacesMessage("Product Edited!", String.valueOf(event.getObject().getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void saveStudent(Student student) {
        try (Connection connection = dataSource.getConnection()) {

            String insertQuery = "INSERT INTO student (name) VALUES (?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, student.getName());
                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Success!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Project> getAssociatedProjects(int id) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Project> projects = new ArrayList<>();

        if (connection != null) {
            try {
                String query = "SELECT p.* FROM project p JOIN project_student ps ON p.id = ps.project_id WHERE ps.student_id = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, id);
                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Project project = new Project(
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

    public void showAssociatedProjects(int studentId) {
        this.selectedStudentId = studentId;
    }
}