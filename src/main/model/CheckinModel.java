package main.model;

import main.Booking;
import main.SQLConnection;
import main.UserHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Date;

public class CheckinModel {

    Connection connection;

    public CheckinModel(){
        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
    }

    // Return if a user has an admin accepted booking today
    public Boolean checkBookingToday() {

        int UserID = UserHolder.getInstance().getUser().getEmployeeId();
        LocalDate dateNow = LocalDate.now();
        try {
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            String query = "select * from DeskBookings " +
                    "where EmployeeID = ? " +
                    "AND AdminAccepted = true " +
                    "AND bookedDate == ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, UserID);
            preparedStatement.setObject(2, dateNow);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {

                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    // Update the lastdesk of a user
    public void setLastdesk(int deskID)
    {
        try {
            int UserID = UserHolder.getInstance().getUser().getEmployeeId();
            PreparedStatement preparedStatement = null;

            String query = "update Employee " +
                    "set LastDesk = ? " +
                    "where id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, deskID);
            preparedStatement.setObject(2, UserID);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // returns an admin accepted booking today
    public Booking getBooking()
    {
        int UserID = UserHolder.getInstance().getUser().getEmployeeId();
        LocalDate date = LocalDate.now();
        Booking booking = new Booking();
        try {
            String query = "select * from DeskBookings " +
                    "where EmployeeID = ? " +
                    "and bookedDate = ? " +
                    "and AdminAccepted = true";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, UserID);
            preparedStatement.setObject(2, date);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                booking.setTableNumber(resultSet.getInt("deskId"));
                booking.setBookingNumber(resultSet.getInt("BookingID"));
                booking.setCheckedIn(resultSet.getBoolean("CheckedIn"));
            }

        } catch (Exception e) {

        }
        return booking;
    }

    // sets a booking to be checked in
    public void checkIn(int bookingID)
    {
        try {
            String query = "UPDATE DeskBookings " +
                    "set CheckedIn = ?" +
                    "where BookingID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setBoolean(1, true);
            preparedStatement.setInt(2, bookingID);
            preparedStatement.executeUpdate();

        } catch (Exception e) {

        }
    }
}
