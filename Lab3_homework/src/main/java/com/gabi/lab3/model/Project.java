package com.gabi.lab3.model;

import com.gabi.lab3.database.DatabaseConnector;
import lombok.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ManagedBean
@SessionScoped
@Data
public class Project implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    private String name;

    private String category;

    private List<String> selectedCategory;

    public String createProject() throws ClassNotFoundException {
        Connection connection = DatabaseConnector.connect();
        selectedCategory = new ArrayList<>();
        selectedCategory.add("adas");
        selectedCategory.add("asdasd");
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                String sqlQuery = "INSERT INTO project (name, category) VALUES ('" + this.name + "', '" + this.category + "');";
                int resultSet = statement.executeUpdate(sqlQuery);
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null; // This can be a navigation outcome if needed, e.g., "success"
    }
}
