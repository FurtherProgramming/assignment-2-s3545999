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

    // return if a user has a last desk
    public boolean checkIfLastDesk(User user)
    {
        boolean lastDesk = false;
        try{
            String query = "select * from Employee where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, user.getEmployeeId());

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.getInt("LastDesk") > 0)
            {
                lastDesk = true;
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return lastDesk;
    }

    // Sets last desk to null
    public void releaseLast(User user)
    {
        try {
            String query = "Update Employee " +
                    "set LastDesk = NULL " +
                    "where Employee.id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, user.getEmployeeId());
            preparedStatement.executeUpdate();

        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    // returns whether a username is taken or not
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

    // Returns all the security questions
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

    // Updates a user by its id
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

    // Adds a user to the database
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
