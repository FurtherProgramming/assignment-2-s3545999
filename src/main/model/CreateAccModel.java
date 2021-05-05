package main.model;


import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public Boolean userTaken(String user) throws SQLException {

        boolean userExists = false;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        String query = "select * from employee where username = ?";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userExists = true;
            }
            else{
                userExists = false;
            }
        }
        catch (Exception e)
        {
            return false;
        }finally {
            preparedStatement.close();
            resultSet.close();
        }
        return userExists;
    }
    public Boolean addUser(User user) throws SQLException {

        PreparedStatement preparedStatement = null;
        System.out.println("Adding1");
        String query = "INSERT INTO Employee (firstName, surname, empRole, username, password, SecQuestion, SecAns) VALUES (?,?,?,?,?,?,?)";
        try {
            System.out.println("Adding2");
            preparedStatement = connection.prepareStatement(query);
            System.out.println("Adding4");
            preparedStatement.setString(1, user.getfName());
            preparedStatement.setString(2, user.getlName());
            preparedStatement.setString(3, "employee");
            preparedStatement.setString(4, user.getUserName());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getSecretQ());
            preparedStatement.setString(7, user.getSecretQAns());

            System.out.println("Adding3");
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
