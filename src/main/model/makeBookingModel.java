package main.model;

import main.Desk;
import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class makeBookingModel {

    Connection connection;

    public makeBookingModel(){
        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
    }

    public List<Desk> getTableAvailability(java.sql.Date date)
    {
        List<Desk> desks = new ArrayList<>();
        try {

            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            String query = "select * from DeskBookings where DATE = ?";
            System.out.println(date);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, date.toString());
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                System.out.println("desk id");
                System.out.println(resultSet.getInt("desk id"));
                System.out.println("Employee id");
                System.out.println(resultSet.getInt("Employee ID"));
                Desk desk = new Desk();
                desk.setUserID(resultSet.getInt("Employee ID"));
                desk.setAvailability(false);
                desk.setDeskID(resultSet.getInt("desk id"));
                desks.add(desk);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return desks;
    }
}
