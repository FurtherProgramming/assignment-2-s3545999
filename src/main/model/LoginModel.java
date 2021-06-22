package main.model;

import main.SQLConnection;
import main.User;
import main.UserHolder;
import org.sqlite.SQLiteConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LoginModel {

    Connection connection;

    public LoginModel(){
        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
    }

    // returns if the username and password match
    // Sets the userHolder
    public Boolean isLogin(String user, String pass) throws SQLException {

        String query = "select * from Employee " +
                "where username = ? and password= ?" +
                "and Active = true";
        try {
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, pass);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {

                User theUser = new User();
                theUser.setEmployeeId(resultSet.getInt("id"));
                theUser.setFirstName(resultSet.getString("firstName"));
                theUser.setLastName(resultSet.getString("surname"));
                theUser.setUserName(resultSet.getString("username"));
                theUser.setRole(resultSet.getString("empRole"));
                theUser.setPassword(resultSet.getString("password"));
                theUser.setAdmin(resultSet.getBoolean("Admin"));
                theUser.setSecretQ(resultSet.getString("secQuestion"));
                theUser.setSecretQAns(resultSet.getString("SecAns"));

                UserHolder holder = UserHolder.getInstance();
                holder.setUser(theUser);
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
            return false;
        }
    }
}
