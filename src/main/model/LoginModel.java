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

    public Boolean isDbConnected(){
        try {
            return !connection.isClosed();
        }
        catch(Exception e){
            return false;
        }
    }

    public Boolean isLogin(String user, String pass) throws SQLException {

        String query = "select * from Employee where username = ? and password= ?";
        try {
            PreparedStatement preparedStatement = null;
            ResultSet resultSet=null;
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, pass);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {

                User theUser = new User();
                theUser.setEmployeeID(resultSet.getInt("id"));
                System.out.println(resultSet.getInt("id"));
                theUser.setfName(resultSet.getString("firstName"));
                theUser.setlName(resultSet.getString("surname"));
                theUser.setUserName(resultSet.getString("username"));
                System.out.println(resultSet.getBoolean("Admin"));
                theUser.setAdmin(resultSet.getBoolean("Admin"));
                theUser.setSecretQ(resultSet.getString("secQuestion"));

                UserHolder holder = UserHolder.getInstance();
                holder.setUser(theUser);

                return true;
            }
            else{
                return false;
            }
        }
        catch (Exception e)
        {   System.out.println(e);
            return false;
        }
    }
}
