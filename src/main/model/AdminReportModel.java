package main.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.SQLConnection;
import main.User;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class AdminReportModel {
    Connection connection;

    public AdminReportModel(){
        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
    }

    public List<String[]> getAllUsers()
    {
        List<String[]> users = new ArrayList<String[]>();
        String[] headings = new String[9];
        headings[0] = "id";
        headings[1] = "firstName";
        headings[2] = "surname";
        headings[3] = "username";
        headings[4] = "SecQuestion";
        headings[5] = "SecAns";
        headings[6] = "password";
        headings[7] = "admin";
        headings[8] = "Active";
        users.add(headings);

        try {
            String query = "select * from Employee";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                String[] user = new String[9];
                user[0] = String.valueOf(resultSet.getInt("id"));
                user[1] = resultSet.getString("firstName");
                user[2] = resultSet.getString("surname");
                user[3] = resultSet.getString("username");
                user[4] = resultSet.getString("SecQuestion");
                user[5] = resultSet.getString("SecAns");
                user[6] = resultSet.getString("password");
                user[7] = String.valueOf(resultSet.getBoolean("admin"));
                user[8] = String.valueOf(resultSet.getBoolean("Active"));
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
