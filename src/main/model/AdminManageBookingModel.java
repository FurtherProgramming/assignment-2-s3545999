package main.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Booking;
import main.SQLConnection;
import main.User;
import main.UserHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class AdminManageBookingModel {
    Connection connection;

    public AdminManageBookingModel(){
        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
    }

    public ObservableList<Booking> getAllBookings()
    {
        ObservableList<Booking> bookings = FXCollections.observableArrayList();
        try {
            String query = "select * from DeskBookings " +
                    "INNER JOIN Employee ON " +
                    "DeskBookings.EmployeeID = Employee.id " +
                    "Where Canceled = 0 " +
                    "and AdminAccepted = 0";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                System.out.println(resultSet.getObject("bookedDate"));
                Booking booking = new Booking();
                booking.setDate(resultSet.getObject("bookedDate"));
                booking.setTableNumber(resultSet.getInt("deskId"));
                booking.setAdminAccepted(resultSet.getBoolean("AdminAccepted"));
                booking.setBookingNumber(resultSet.getInt("BookingID"));
                booking.setFirstName(resultSet.getString("firstName"));
                booking.setLastName(resultSet.getString("surname"));
                bookings.add(booking);
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return bookings;
    }

    public void confirm(int bookingId)
    {
        try {
            PreparedStatement preparedStatement = null;
            UserHolder holder = UserHolder.getInstance();
            String query = "Update DeskBookings set AdminAccepted = true where BookingID == ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, bookingId);
            preparedStatement.executeUpdate();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public void reject(int bookingId)
    {
        try {
            PreparedStatement preparedStatement = null;
            UserHolder holder = UserHolder.getInstance();
            String query = "Update DeskBookings set Canceled = true where BookingID == ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, bookingId);
            preparedStatement.executeUpdate();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

}
