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

    public Boolean userTaken(String user){

        boolean userExists = false;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from employee where username = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user);

            resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
        catch (Exception e)
        {
            return false;
        }finally {
            assert preparedStatement != null;
            try {
                preparedStatement.close();


            assert resultSet != null;
            resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public Boolean addUser(User user) {

        PreparedStatement preparedStatement = null;

        String query = "INSERT INTO Employee (firstName, surname, empRole, username, password, SecQuestion, SecAns) VALUES (?,?,?,?,?,?,?)";
        try {

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, user.getfName());
            preparedStatement.setString(2, user.getlName());
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
