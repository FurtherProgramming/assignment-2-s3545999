package main.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.SQLConnection;
import main.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;


public class EmployeeManageAccountModel {
    Connection connection;

    public EmployeeManageAccountModel(){
        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
    }

    public List<String> getSecQuestions()
    {
        List<String> questions = FXCollections.observableArrayList();
        try {
            String query = "select * from SecurityQuestions";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                questions.add(resultSet.getString("question"));
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return questions;
    }

    public boolean updateUser(User user)
    {
        String name = user.getFirstName();
        try {

            System.out.println(user.getFirstName());
            System.out.println(user.getEmployeeId());

            String query = "Update Employee " +
                    "set firstName = ?, " +
                    "surname = ?, " +
                    "username = ?, " +
                    "password = ?, " +
                    "SecQuestion = ?, " +
                    "SecAns = ?, " +
                    "Admin = ? " +
                    "where Employee.id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            System.out.println(name);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getUserName());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getSecretQ());
            preparedStatement.setString(6, user.getSecretQAns());
            preparedStatement.setBoolean(7, user.getAdmin());
            preparedStatement.setInt(8, user.getEmployeeId());
            preparedStatement.executeUpdate();

            return true;
        }
        catch(Exception e)
        {
            System.out.println(e);
            return false;
        }
    }

    public boolean addUser(User user)
    {
        try {
            String query = "INSERT INTO Employee " +
                    "(firstName, surname, username, password, SecQuestion, SecAns, Admin) " +
                    "VALUES (?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getUserName());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getSecretQ());
            preparedStatement.setString(6, user.getSecretQAns());
            preparedStatement.setBoolean(7, user.getAdmin());

            preparedStatement.executeUpdate();
            return true;
        }
        catch(Exception e)
        {
            System.out.println(e);
            return false;
        }
    }
}
