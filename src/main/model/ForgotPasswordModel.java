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

    public Boolean isDbConnected(){
        try {
            return !connection.isClosed();
        }
        catch(Exception e){
            return false;
        }
    }

    public Boolean userExists(String user) throws SQLException {

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

    public void selectUser(String username) throws SQLException {

        String query = "select id from Employee where username = ?";
        try {
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userID = resultSet.getInt("id");
            }
        }
        catch (Exception e)
        {

        }
    }

    public String getSecQuestion()
    {
        try {
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            String query = "select SecQuestion from Employee where id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userID);

            resultSet = preparedStatement.executeQuery();

            return resultSet.getString("SecQuestion");
        }
        catch (Exception e)
        {
            return "";
        }
    }

    public boolean checkSecAns(String secAns) throws SQLException {

        String query = "select * from Employee where id = ? and SecAns= ?";
        try {
            PreparedStatement preparedStatement = null;
            ResultSet resultSet=null;
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userID);
            preparedStatement.setString(2, secAns);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User theUser = new User();
                theUser.setEmployeeId(userID);
                UserHolder holder = UserHolder.getInstance();
                holder.setUser(theUser);
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (SQLException e)
        {
            return false;
        }
    }
}


