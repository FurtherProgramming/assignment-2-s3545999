package main.model;

import main.SQLConnection;
import main.User;
import main.UserHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ChangePasswordModel {
    Connection connection;

    public ChangePasswordModel(){
        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
    }

    public boolean setPassword(String newPassword)
    {
        try {
            PreparedStatement preparedStatement = null;
            UserHolder holder = UserHolder.getInstance();

            User user = holder.getUser();
            int id = user.getEmployeeId();
            String query = "Update Employee set password = ? where Employee.id == ?";
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
