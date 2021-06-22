package main.model;

import com.sun.codemodel.internal.JTryBlock;
import main.Booking;
import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

// Lockdown model to set which tables are available at the time
public class AdminLockdownModel {

    Connection connection;

    public AdminLockdownModel(){
        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
    }

    // Return whether a full lockdown is in effect
    public boolean isLockdown()
    {
        Boolean isLockdown = true;
        try
        {
            String query = "select * from deskAvailability where covidAvailability = true";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
            {
                isLockdown = false;
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return isLockdown;
    }

    // return if all tables are currently available
    public boolean isAllAvailable()
    {
        Boolean isAvailable = true;
        ArrayList<Boolean> lockdown = new ArrayList<>();
        try
        {
            String query = "select * from deskAvailability where covidAvailability = false";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
            {
                isAvailable = false;
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return isAvailable;
    }

    // Set the lockdown
    public void setLockdown()
    {
        try
        {
            String query = "update deskAvailability set covidAvailability = false";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();

        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    // set all tables available
    public void setAllAvailable()
    {
        try
        {
            String query = "update deskAvailability set covidAvailability = true";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();

        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    // set alternating desk available
    public void setCovidSafe() {

        setLockdown();
        try {
            String query = "update deskAvailability " +
                    "SET covidAvailability = true " +
                    "where deskNumber = ? " +
                    "or deskNumber = ? " +
                    "or deskNumber = ? " +
                    "or deskNumber = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,1);
            preparedStatement.setInt(2,3);
            preparedStatement.setInt(3,6);
            preparedStatement.setInt(4,8);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Set all bookings to canceled
    public void cancelAllBookings()
    {
        LocalDate date = LocalDate.now();
        try
        {
            String query = "update DeskBookings " +
                    "SET Canceled = true, " +
                    "AdminAccepted = false " +
                    "where bookedDate > ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, date);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }

    }

}
