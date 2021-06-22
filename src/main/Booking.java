package main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;

public class Booking {

    private int bookingNumber;
    private Object date;
    private int TableNumber;
    private int employeeID;
    private boolean checkedIn;
    private boolean adminAccepted;
    private boolean canceled;
    private String firstName;
    private String lastName;

    public Booking(){

    }

    public int getTableNumber() {
        return TableNumber;
    }

    public void setTableNumber(int tableNumber) {
        TableNumber = tableNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public boolean isAdminAccepted() {
        return adminAccepted;
    }

    public void setAdminAccepted(boolean adminAccepted) {
        this.adminAccepted = adminAccepted;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public Object getDate() {
        return date;
    }

    public void setDate(Object date) {
        this.date = date;
    }

    public int getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(int bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public boolean isCheckedIn() { return checkedIn; }

    public void setCheckedIn(boolean checkedIn) { this.checkedIn = checkedIn; }
}
