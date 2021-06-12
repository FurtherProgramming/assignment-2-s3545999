package main.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.SQLConnection;
import main.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class CreateManageAccountModel {
    Connection connection;

    public CreateManageAccountModel(){
        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
    }

    public boolean checkusername(String user)
    {
        boolean taken = false;
        try{
            String query = "select * from Employee where username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                taken = true;
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return taken;
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
        try {

            String query = "Update Employee " +
                    "set firstName = ?, " +
                    "surname = ?, " +
                    "username = ?, " +
                    "empRole = ?, " +
                    "password = ?, " +
                    "SecQuestion = ?, " +
                    "SecAns = ?, " +
                    "Admin = ? " +
                    "where Employee.id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getUserName());
            preparedStatement.setString(4, user.getRole());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getSecretQ());
            preparedStatement.setString(7, user.getSecretQAns());
            preparedStatement.setBoolean(8, user.getAdmin());
            preparedStatement.setInt(9, user.getEmployeeId());
            preparedStatement.executeUpdate();

            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public boolean addUser(User user)
    {
        try {
            String query = "INSERT INTO Employee " +
                    "(firstName, surname, username, empRole,password, SecQuestion, SecAns, Admin) " +
                    "VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getUserName());
            preparedStatement.setString(4, user.getRole());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getSecretQ());
            preparedStatement.setString(7, user.getSecretQAns());
            preparedStatement.setBoolean(8, user.getAdmin());

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
