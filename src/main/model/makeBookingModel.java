package main.model;

import main.Desk;
import main.SQLConnection;
import main.UserHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class makeBookingModel {

    Connection connection;
    Connection connection1;

    public makeBookingModel(){
//        if (connection == null)
//            System.exit(1);
    }

//    public Boolean checkBooking() {
//
//        int UserID = UserHolder.getInstance().getUser().getEmployeeID();
//        try {
//            PreparedStatement preparedStatement = null;
//            ResultSet resultSet = null;
//            String query = "select * from DeskBookings where 'Employee ID' = ? AND checked = false";
//            preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setInt(1, UserID);
//            resultSet = preparedStatement.executeQuery();
//            while(resultSet.next())
//            {
//                return true;
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        return false;
//    }

    public List<Desk> getTableAvailability(String date)
    {
        connection = SQLConnection.connect();
        List<Desk> desks = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {

            String query = "select * from DeskBookings where DATE = ?";
            System.out.println(date);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, date);
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
            preparedStatement.close();
            resultSet.close();
            connection.close();
            return desks;
        } catch (Exception e) {
            System.out.println(e);
        }
        return desks;
    }

    public boolean makeBooking(int deskId, String date)
    {
        connection1 = SQLConnection.connect();
        int userId = UserHolder.getInstance().getUser().getEmployeeID();
        String query = "INSERT INTO DeskBookings (date, deskId, EmployeeId, CheckedIn, AdminAccepted) VALUES (?,?,?,?,?)";
        try (PreparedStatement preparedStatement = connection1.prepareStatement(query)){

            preparedStatement.setString(1, date);
            preparedStatement.setInt(2, deskId);
            preparedStatement.setInt(3, userId);
            preparedStatement.setBoolean(4, false);
            preparedStatement.setBoolean(5, false);
            preparedStatement.executeUpdate();
            return true;


        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
}
