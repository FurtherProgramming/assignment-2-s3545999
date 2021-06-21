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
import java.time.LocalDate;


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
        LocalDate date = LocalDate.now();
        try {
            String query = "select * from DeskBookings " +
                    "INNER JOIN Employee ON " +
                    "DeskBookings.EmployeeID = Employee.id " +
                    "where bookedDate > ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, date);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {

                Booking booking = new Booking();
                booking.setDate(resultSet.getObject("bookedDate"));
                booking.setTableNumber(resultSet.getInt("deskId"));
                booking.setAdminAccepted(resultSet.getBoolean("AdminAccepted"));
                booking.setEmployeeID(resultSet.getInt("EmployeeID"));
                booking.setBookingNumber(resultSet.getInt("BookingID"));
                booking.setFirstName(resultSet.getString("firstName"));
                booking.setLastName(resultSet.getString("surname"));
                booking.setCanceled(resultSet.getBoolean("Canceled"));
                bookings.add(booking);
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return bookings;
    }

    public boolean checkAccepted(int userID)
    {
        boolean exists = false;
        LocalDate date = LocalDate.now();
        try {
            PreparedStatement preparedStatement = null;
            UserHolder holder = UserHolder.getInstance();
            String query = "SELECT * from DeskBookings " +
                    "where EmployeeID = ? " +
                    "and AdminAccepted = true " +
                    "and bookedDate > ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userID);
            preparedStatement.setObject(2, date);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                exists = true;
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        return exists;
    }

    public boolean checkNotRejected(int userID)
    {
        boolean exists = false;
        LocalDate date = LocalDate.now();
        try {
            PreparedStatement preparedStatement = null;
            String query = "SELECT * from DeskBookings " +
                    "where EmployeeID = ? " +
                    "and AdminAccepted = false " +
                    "and Canceled = false " +
                    "and bookedDate > ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userID);
            preparedStatement.setObject(2, date);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                exists = true;
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        return exists;
    }

    public void confirm(int bookingId)
    {
        try {
            PreparedStatement preparedStatement = null;
            String query = "Update DeskBookings set AdminAccepted = true, Canceled = false where BookingID = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, bookingId);
            preparedStatement.executeUpdate();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public boolean checkTableAvailable(Booking booking)
    {
        boolean available = true;
        try {
            PreparedStatement preparedStatement = null;
            String query = "SELECT * from DeskBookings " +
                    "where AdminAccepted = true " +
                    "and Canceled = false " +
                    "and deskId = ?" +
                    "and bookedDate = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, booking.getTableNumber());
            preparedStatement.setObject(2, booking.getDate());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                available = false;
            }
        }
        catch(Exception e){
            System.out.println(e);
        }

        if(available == true)
        {
            try {
                PreparedStatement preparedStatement = null;
                String query = "SELECT * from deskAvailability " +
                        "where deskNumber = ?" +
                        "and covidAvailability = false";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, booking.getTableNumber());
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next())
                {
                    available = false;
                }
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
        return available;
    }


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
                has = true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return has;
    }


    public boolean isPrevTable(int userId, int tableID)
    {
        boolean prevTable = false;
        try {
            int id = UserHolder.getInstance().getUser().getEmployeeId();
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            String query = "select * from Employee " +
                    "where id = ?" +
                    "and LastDesk = ?";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, tableID);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                prevTable = true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return prevTable;
    }

    public void reject(int bookingId)
    {
        try {
            PreparedStatement preparedStatement = null;
            UserHolder holder = UserHolder.getInstance();
            String query = "Update DeskBookings set Canceled = true, AdminAccepted = false where BookingID = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, bookingId);
            preparedStatement.executeUpdate();

        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public boolean delete(int bookingId)
    {
        boolean success = false;
        try {
            PreparedStatement preparedStatement = null;
            UserHolder holder = UserHolder.getInstance();
            String query = "DELETE from DeskBookings where BookingID = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, bookingId);
            preparedStatement.executeUpdate();
            success = true;
        }
        catch(Exception e){
            System.out.println(e);
        }
        return success;
    }
}
