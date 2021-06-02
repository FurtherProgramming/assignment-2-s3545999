package main.model;

import main.Desk;
import main.SQLConnection;
import main.UserHolder;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class makeBookingModel {

    Connection connection;

    public makeBookingModel(){
        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
    }

    public String getBooking()
    {
        String returnString = "";
        String bookedDate  = "";
        Integer tableNum = -1;
        int UserID = UserHolder.getInstance().getUser().getEmployeeId();
        System.out.println(UserID);
        java.sql.Date dateNow = new java.sql.Date(new java.util.Date().getTime());

        try {
            String query = "select * from DeskBookings where EmployeeID = ? and bookedDate > ? and CheckedIn = 0 and Canceled = 0";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, UserID);
            preparedStatement.setDate(2, dateNow);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                bookedDate = resultSet.getObject("bookedDate").toString();
                tableNum = resultSet.getInt("deskId");
                returnString = "You have booked table Number: " + tableNum + " for " + bookedDate;
            }

        } catch (Exception e) {

        }
        return returnString;
    }

    public Boolean checkBooking() {

        int UserID = UserHolder.getInstance().getUser().getEmployeeId();
        System.out.println(UserID);
        try {
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            String query = "select * from DeskBookings where EmployeeID = ? AND CheckedIn = 0 AND Canceled = false";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, UserID);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                System.out.println();
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public List<Desk> getTableAvailability(LocalDate date)
    {
        List<Desk> desks = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String query = "select * from DeskBookings where bookedDate = ? and Canceled = false";
            System.out.println(date);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, date);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                System.out.println("deskId");
                System.out.println(resultSet.getInt("deskId"));
                System.out.println("Employee id");
                System.out.println(resultSet.getInt("EmployeeID"));
                Desk desk = new Desk();
                desk.setUserID(resultSet.getInt("EmployeeID"));
                desk.setAvailability(false);
                desk.setDeskID(resultSet.getInt("deskId"));
                desks.add(desk);
            }
            preparedStatement.close();
            resultSet.close();
            return desks;
        } catch (Exception e) {
            System.out.println(e);
            return desks;
        }
    }

    public boolean makeBooking(int deskId, LocalDate date)
    {
        int userId = UserHolder.getInstance().getUser().getEmployeeId();
        String query = "INSERT INTO DeskBookings (bookedDate, deskId, EmployeeId, CheckedIn, AdminAccepted) VALUES (?,?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){

            preparedStatement.setObject(1, date);
            preparedStatement.setInt(2, deskId);
            preparedStatement.setInt(3, userId);
            preparedStatement.setBoolean(4, false);
            preparedStatement.setBoolean(5, false);
            preparedStatement.executeUpdate();
            System.out.println("ADDED");
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

    }
    public boolean cancelBooking()
    {
        boolean success = false;
        int UserID = UserHolder.getInstance().getUser().getEmployeeId();
        System.out.println(UserID);
        java.sql.Date dateNow = new java.sql.Date(new java.util.Date().getTime());

        try {
            String query = "UPDATE DeskBookings " +
                    "SET Canceled = true " +
                    "where EmployeeID = ? " +
                    "AND bookedDate > ? " +
                    "AND CheckedIn = 0 " +
                    "AND Canceled = 0";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, UserID);
            preparedStatement.setDate(2, dateNow);
            if(preparedStatement.executeUpdate() == 1)
            {
                success = true;
            }
        }catch (Exception e) {
            System.out.println(e);
        }
        return success;
    }
}
