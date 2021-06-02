package main.model;


import main.SQLConnection;

import java.sql.*;

import main.User;

public class CreateAccModel {
    Connection connection;

    public CreateAccModel(){
        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
    }

    public Boolean isDbConnected(){
        try {
            return !connection.isClosed();
        }
        catch(Exception e){
            return false;
        }
    }

    public Boolean userTaken(String user){

        boolean userExists = false;

        String query = "select * from employee where username = ?";
        try {
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user);

            resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public Boolean addUser(User user) {

        String query = "INSERT INTO Employee (firstName, surname, empRole, username, password, SecQuestion, SecAns) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = null;
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, "employee");
            preparedStatement.setString(4, user.getUserName());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getSecretQ());
            preparedStatement.setString(7, user.getSecretQAns());

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
