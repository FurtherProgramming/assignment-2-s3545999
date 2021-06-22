package main.model;

import main.SQLConnection;
import main.User;
import main.UserHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ForgotPasswordModel {

    Connection connection;
    private int userID;

    public ForgotPasswordModel(){

        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);

    }

    // Return if a user exists by username
    public Boolean userExists(String user){

        String query = "select * from Employee where username = ?";
        try {
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
            else {
                return false;
            }
        }
        catch (Exception e)
        {
            return false;
        }
    }

    // Get a user from their username
    public User getUser(String username){

        User user = new User();
        String query = "select * from Employee where username = ?";
        try {
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user.setEmployeeId(resultSet.getInt("id"));
                user.setSecretQ(resultSet.getString("SecQuestion"));
                user.setSecretQAns(resultSet.getString("SecAns"));
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return user;
    }

    // Set the password of a user
    public boolean setPassword(String newPassword, User user)
    {
        try {
            PreparedStatement preparedStatement = null;

            int id = user.getEmployeeId();
            String query = "Update Employee set password = ? where Employee.id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newPassword);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            return true;
        }
        catch (Exception e)
        {
            System.out.println(e);
            return false;
        }
    }

}


