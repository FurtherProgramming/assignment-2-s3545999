package main.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.SQLConnection;
import main.User;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class AdminReportModel {
    Connection connection;

    public AdminReportModel(){
        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
    }

    public ArrayList<ArrayList<String>> getAllUsers()
    {

        ArrayList<ArrayList<String>> users = new ArrayList<ArrayList<String>>();
        ArrayList<String > headers = new ArrayList<String >();
        headers.add("id");
        headers.add("firstName");
        headers.add("surname");
        headers.add("username");
        headers.add("SecQuestion");
        headers.add("SecAns");
        headers.add("password");
        headers.add("admin");
        headers.add("Active");
        users.add(headers);

        try {
            String query = "select * from Employee";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                ArrayList<String > user = new ArrayList<>();
                user.add(String.valueOf(resultSet.getInt("id")));
                user.add(resultSet.getString("firstName"));
                user.add(resultSet.getString("surname"));
                user.add(resultSet.getString("username"));
                user.add(resultSet.getString("SecQuestion"));
                user.add(resultSet.getString("SecAns"));
                user.add(resultSet.getString("password"));
                user.add(String.valueOf(resultSet.getBoolean("admin")));
                user.add(String.valueOf(resultSet.getBoolean("Active")));
                users.add(user);
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return users;
    }

    public ArrayList<ArrayList<String>> getBookingBetweenDates(LocalDate beforeDate, LocalDate afterDate)
    {

        ArrayList<ArrayList<String>> bookings = new ArrayList<ArrayList<String>>();
        ArrayList<String > headers = new ArrayList<String >();
        headers.add("Booking ID");
        headers.add("Booking Date");
        headers.add("Desk ID");
        headers.add("Employee ID");
        headers.add("CheckedIn");
        headers.add("Admin Accepted");
        headers.add("Canceled");
        bookings.add(headers);


        System.out.println(beforeDate);
        System.out.println(afterDate);
        try {
            String query = "select * from DeskBookings " +
                    "where bookedDate >= ? " +
                    "AND bookedDate <= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, beforeDate);
            preparedStatement.setObject(2, afterDate);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                ArrayList<String > booking = new ArrayList<>();
                booking.add(String.valueOf(resultSet.getInt("BookingID")));
                booking.add(String.valueOf(resultSet.getObject("bookedDate")));
                booking.add(String.valueOf(resultSet.getInt("deskId")));
                booking.add(String.valueOf(resultSet.getInt("EmployeeID")));
                booking.add(String.valueOf(resultSet.getBoolean("CheckedIn")));
                booking.add(String.valueOf(resultSet.getBoolean("AdminAccepted")));
                booking.add(String.valueOf(resultSet.getBoolean("Canceled")));
                bookings.add(booking);
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return bookings;
    }

}
