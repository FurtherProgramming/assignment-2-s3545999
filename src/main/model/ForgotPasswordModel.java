package main.model;

import main.SQLConnection;
import main.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ForgotPasswordModel {

    Connection connection;

    public ForgotPasswordModel(){


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

    public Boolean userExists(String user) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        String query = "select * from Employee where username = ?";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
            else{
                return false;
            }
        }
        catch (Exception e)
        {
            return false;
        }finally {
           preparedStatement.close();
           resultSet.close();
        }

    }

    public User getUser(String username) throws SQLException {
        User changeUser;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select SecQuestion from Employee where username = ?";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User changeUSer = new User(resultSet.getString("firstName"),resultSet.getString("surname"),
                        resultSet.getString("username"),resultSet.getString("password"),resultSet.getString("secQuestion"),
                        resultSet.getString("secAns"));
                return changeUSer;
            }
        }
        catch (Exception e)
        {
        }
        finally
        {
            preparedStatement.close();
            resultSet.close();
        }
        return changeUser;
    }
}
