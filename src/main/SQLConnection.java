package main;
import java.sql.*;

public class SQLConnection {
    private static Connection connection;

    public static Connection connect(){
        try {
            if (connection == null) {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:assignment.db");
                return connection;
            } else {
                connection.close();
                connection = DriverManager.getConnection("jdbc:sqlite:assignment.db");
                return connection;
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
            return null;
        }
    }
}
