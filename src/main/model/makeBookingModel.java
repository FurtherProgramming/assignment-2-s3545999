package main.model;

import main.Booking;
import main.SQLConnection;
import main.UserHolder;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class makeBookingModel {

    Connection connection;

    public makeBookingModel(){
        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
    }

    // Returns if lockdown is on
    public boolean isLockdown()
    {
        Boolean Lockdown = true;
        try
        {
            String query = "select * from deskAvailability where covidAvailability = true";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
            {
                Lockdown = false;
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return Lockdown;
    }


    // Get a users booking
    public Booking getBooking()
    {
        int UserID = UserHolder.getInstance().getUser().getEmployeeId();
        LocalDate dateNow = LocalDate.now();
        Booking booking = new Booking();
        try {
            String query = "select * from DeskBookings " +
                    "where EmployeeID = ? " +
                    "and bookedDate > ? " +
                    "and CheckedIn = 0 " +
                    "and Canceled = 0";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, UserID);
            preparedStatement.setObject(2, dateNow);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                booking.setBookingNumber(resultSet.getInt("BookingID"));
                booking.setTableNumber(resultSet.getInt("deskId"));
                booking.setDate(resultSet.getObject("bookedDate"));
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return booking;
    }

    // Returns if a user has a booking after the date
    public Boolean checkBooking(LocalDate date) {

        boolean check = false;
        int UserID = UserHolder.getInstance().getUser().getEmployeeId();

        try {
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            String query = "select * from DeskBookings " +
                    "where EmployeeID = ? " +
                    "AND CheckedIn = 0 " +
                    "AND Canceled = false " +
                    "AND bookedDate > ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, UserID);
            preparedStatement.setObject(2, date);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                check =  true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return check;
    }

    // Returns all bookings on a date, including lockdown
    public List<Booking> getTableAvailability(LocalDate date)
    {
        ArrayList<Booking> bookings = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            String query = "select * from DeskBookings " +
                    "where bookedDate = ? " +
                    "and Canceled = false";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, date);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                Booking booking = new Booking();
                booking.setEmployeeID(resultSet.getInt("EmployeeID"));
                booking.setTableNumber(resultSet.getInt("deskId"));
                bookings.add(booking);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            String query = "select * from deskAvailability " +
                    "where covidAvailability = false ";

            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                Booking booking = new Booking();
                booking.setTableNumber(resultSet.getInt("deskNumber"));
                bookings.add(booking);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        if(hasPrevBooking())
        {
            bookings.add(getPrevTable());
        }

        return bookings;
    }

    // Returns if a user has a previous table they can't sit on
    public boolean hasPrevBooking()
    {
        boolean has = false;
        try {
            int id = UserHolder.getInstance().getUser().getEmployeeId();
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            String query = "select LastDesk from Employee " +
                    "where id = ?";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                if(resultSet.getString("LastDesk") != null)
                {
                    has = true;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return has;
    }

    // Returns the previous table as a booking
    public Booking getPrevTable()
    {
        Booking booking = new Booking();;
        try {
            int id = UserHolder.getInstance().getUser().getEmployeeId();
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            String query = "select LastDesk from Employee " +
                    "where id = ?";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                booking.setTableNumber(resultSet.getInt("LastDesk"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return booking;
    }

    // make a booking on the date and desk number
    public boolean makeBooking(int deskId, LocalDate date)
    {
        boolean made = false;
        int userId = UserHolder.getInstance().getUser().getEmployeeId();
        String query = "INSERT INTO DeskBookings (bookedDate, deskId, EmployeeId, CheckedIn, AdminAccepted) " +
                "VALUES (?,?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){

            preparedStatement.setObject(1, date);
            preparedStatement.setInt(2, deskId);
            preparedStatement.setInt(3, userId);
            preparedStatement.setBoolean(4, false);
            preparedStatement.setBoolean(5, false);
            preparedStatement.executeUpdate();

            made = true;
        } catch (Exception e) {
            System.out.println(e);

        }
        return made;
    }

    // Deletes a booking on its bookingID
    public boolean deleteBooking(Booking booking)
    {
        boolean success = false;

        try {
            String query = "DELETE from DeskBookings " +
                    "where BookingID = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, booking.getBookingNumber());

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
