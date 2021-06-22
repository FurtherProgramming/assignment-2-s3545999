package main.model;

import main.SQLConnection;
import main.UserHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

public class cancelBookingModel {

    Connection connection;

    public cancelBookingModel(){
        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
    }

    public Boolean checkBooking() {

        int UserID = UserHolder.getInstance().getUser().getEmployeeId();
        System.out.println(UserID);
        try {
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            String query = "select * from DeskBookings " +
                    "where EmployeeID = ? " +
                    "AND CheckedIn = false " +
                    "AND Canceled = false";
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

    public String getBooking()
    {
        String returnString = "";
        String bookedDate  = "";
        Integer tableNum = -1;
        int UserID = UserHolder.getInstance().getUser().getEmployeeId();
        System.out.println(UserID);
        java.sql.Date dateNow = new java.sql.Date(new Date().getTime());

        try {
            String query = "select * from DeskBookings " +
                    "where EmployeeID = ? " +
                    "and bookedDate > ? " +
                    "and CheckedIn = 0 " +
                    "and Canceled = 0";
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

    public boolean cancelBooking()
    {
        boolean success = false;
        int UserID = UserHolder.getInstance().getUser().getEmployeeId();
        System.out.println(UserID);
        java.sql.Date dateNow = new java.sql.Date(new Date().getTime());

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
