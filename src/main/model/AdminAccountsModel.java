package main.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.SQLConnection;
import main.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class AdminAccountsModel {
    Connection connection;

    public AdminAccountsModel(){
        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
    }

    public User getUser(int userID)
    {
        User user = new User();
        try {
            String query = "select * from Employee " +
                    "where id == ? ";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                user.setFirstName(resultSet.getString("firstName"));
                user.setLastName(resultSet.getString("surname"));
                user.setUserName(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setSecretQ(resultSet.getString("SecQuestion"));
                user.setEmployeeId(resultSet.getInt("id"));
                user.setSecretQAns(resultSet.getString("SecAns"));
                user.setAdmin(resultSet.getBoolean("Admin"));
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return user;
    }

    public ObservableList<User> getAllUsers()
    {
        ObservableList<User> users = FXCollections.observableArrayList();
        try {
            String query = "select * from Employee";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                User user = new User();
                user.setEmployeeId(resultSet.getInt("id"));
                user.setFirstName(resultSet.getString("firstName"));
                user.setLastName(resultSet.getString("surname"));
                user.setUserName(resultSet.getString("username"));
                users.add(user);
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return users;
    }

}
