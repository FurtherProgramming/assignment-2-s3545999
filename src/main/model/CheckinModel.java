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
                System.out.println(booking.isCheckedIn());
            }

        } catch (Exception e) {

        }
        return booking;
    }

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
