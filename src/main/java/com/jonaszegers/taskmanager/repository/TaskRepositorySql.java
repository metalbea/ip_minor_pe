package com.jonaszegers.taskmanager.repository;

import com.jonaszegers.taskmanager.domain.model.SubTask;
import com.jonaszegers.taskmanager.domain.model.Task;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Repository
public class TaskRepositorySql implements TaskRepository {

    private Properties properties;
    private String url = "jdbc:postgresql://databanken.ucll.be:51920/2TX33?sslmode=require";

    public TaskRepositorySql() {
        this.properties = new Properties();
        properties.setProperty("url", url);
        properties.setProperty("user", "r0744543");
        properties.setProperty("password", "wachtwoord");//is niet mijn echte ww
        properties.setProperty("currentSchema", "JonasZegersWeb3");
        properties.setProperty("ssl", "true");
        properties.setProperty("sslmode", "prefer");
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private List<Task> createListFromResultset(ResultSet resultSet) throws SQLException {
        List<Task> products = new ArrayList<>();
        while (resultSet.next()) {
            String title = resultSet.getString("tasktitle");
            String description = resultSet.getString("taskdescription");
            int id = resultSet.getInt("taskid");
            Timestamp dueDate = resultSet.getTimestamp("taskduedate");
            Task product = new Task(id, title, description, dueDate.toLocalDateTime());
            products.add(product);
        }
        return products;
    }

    private List<SubTask> createSubTaskListFromResultset(ResultSet resultSet) throws SQLException {
        List<SubTask> subTasks = new ArrayList<>();
        while (resultSet.next()) {
            String title = resultSet.getString("subtasktitle");
            String description = resultSet.getString("subtaskdescription");
            int id = resultSet.getInt("subtaskid");
            SubTask subTask = new SubTask(id, title, description);
            subTasks.add(subTask);
        }
        return subTasks;
    }

    @Override
    public List<Task> getTasks() {
        String query = "SELECT * FROM \"JonasZegersWeb3\".\"Task\"";
        try (Connection connection = DriverManager.getConnection(url, properties);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            return createListFromResultset(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

    }

    @Override
    public Task getTask(int id) {
        String query = "SELECT * FROM \"JonasZegersWeb3\".\"Task\" where taskid = " + id;
        try (Connection connection = DriverManager.getConnection(url, properties);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            String title = resultSet.getString("tasktitle");
            String description = resultSet.getString("taskdescription");
            Timestamp dueDate = resultSet.getTimestamp("taskduedate");
            if(title==null) return null;
            return new Task(id, title, description, dueDate.toLocalDateTime());
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public void editTask(Task t) {
        String query = "UPDATE \"JonasZegersWeb3\".\"Task\" set tasktitle = ?, taskdescription = ?, taskduedate = ? where taskid = ?";
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, t.getTitle());
            statement.setString(2, t.getDescription());
            statement.setTimestamp(3, Timestamp.valueOf(t.getDueDate()));
            statement.setInt(4, t.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void addTask(Task t) {
        String query = "insert into \"JonasZegersWeb3\".\"Task\" (tasktitle, taskdescription, taskduedate) values (?,?,?)";
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, t.getTitle());
            statement.setString(2, t.getDescription());
            statement.setTimestamp(3, Timestamp.valueOf(t.getDueDate()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public List<SubTask> getSubtasks(int taskId) {
        String query = "SELECT * FROM \"JonasZegersWeb3\".\"Subtask\" where taskid = " + taskId;
        try (Connection connection = DriverManager.getConnection(url, properties);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            return createSubTaskListFromResultset(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public SubTask getSubTask(int subId) {
        String query = "SELECT * FROM \"JonasZegersWeb3\".\"Subtask\" where subtaskid = " + subId;
        try (Connection connection = DriverManager.getConnection(url, properties);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            return createSubTaskListFromResultset(resultSet).get(0);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void addSubTask(int id, SubTask s) {
        String query = "insert into \"JonasZegersWeb3\".\"Subtask\" (subtasktitle, subtaskdescription, taskid) values (?,?,?)";
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, s.getTitle());
            statement.setString(2, s.getDescription());
            statement.setInt(3, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void editSubTask(int id, SubTask s) {
        String query = "UPDATE \"JonasZegersWeb3\".\"Subtask\" set subtasktitle = ?, subtaskdescription = ? where subtaskid = ?";
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, s.getTitle());
            statement.setString(2, s.getDescription());
            statement.setInt(3, s.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void removeTask(int id) {
        String query = "DELETE from \"JonasZegersWeb3\".\"Task\" where taskid = ?;delete from \"JonasZegersWeb3\".\"Subtask\" where taskid = ?";
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void removeSubTask(int id, int subId) {
        String query = "delete from \"JonasZegersWeb3\".\"Subtask\" where subtaskid = ?";
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, subId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
