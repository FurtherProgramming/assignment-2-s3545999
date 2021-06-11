package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import main.Booking;
import main.UserHolder;
import main.model.makeBookingModel;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;

import java.util.*;

// Graphical representation and desk booking
public class makeBookingController implements Initializable {
    public makeBookingModel makeModel = new makeBookingModel();

    private List<Rectangle> rectangles = new ArrayList<>();
    private Rectangle currentRectangle;
    private LocalDate date;

    private boolean lockdown;

    List<Booking> currentBookings;
    Booking currentUserBooking;

    @FXML
    DatePicker datePicker;
    @FXML
    Rectangle Table1;
    @FXML
    Rectangle Table2;
    @FXML
    Rectangle Table3;
    @FXML
    Rectangle Table4;
    @FXML
    Rectangle Table5;
    @FXML
    Rectangle Table6;
    @FXML
    Rectangle Table7;
    @FXML
    Rectangle Table8;
    @FXML
    Label booking;
    @FXML
    Label changeTXT;
    @FXML
    Label HeaderTXT;
    @FXML
    Button cancel;
    @FXML
    Button submitter;

    // Initialise the rectangles
    public void initialize(URL location, ResourceBundle resources) {
        rectangles.add(Table1);
        rectangles.add(Table2);
        rectangles.add(Table3);
        rectangles.add(Table4);
        rectangles.add(Table5);
        rectangles.add(Table6);
        rectangles.add(Table7);
        rectangles.add(Table8);
        setAllTab(Color.GRAY);
        lockdown = makeModel.isLockdown();

        for (int i = 0; i < rectangles.size(); i++) {
            rectangles.get(i).setMouseTransparent(true);
        }
        if (!UserHolder.getInstance().getUser().getAdmin()) {
            if (!lockdown) {
                if (!makeModel.checkBooking(LocalDate.now())) {
                    cancel.setVisible(false);
                    booking.setVisible(false);
                    changeTXT.setText("You don't have a Booking! Do you want to make one?");
                } else {
                    currentUserBooking = makeModel.getBooking();
                    String bookingText = "You have booked desk " + currentUserBooking.getTableNumber() +
                            " for " + currentUserBooking.getDate().toString();
                    booking.setText(bookingText);
                }

                if (makeModel.checkBooking(LocalDate.now()) && !makeModel.checkBooking(LocalDate.now().plusDays(2))) {
                    disableBooking();
                    changeTXT.setText("You have a booking in the next two days!\n\n" +
                            "You must cancel your booking before rebooking!");
                }
            } else {
                disableBooking();
                booking.setVisible(false);
                cancel.setVisible(false);
                changeTXT.setText("You cannot book due to lockdown!");
            }
        } else {
            adminSetUp();
        }
    }

    // Set up page for an admin
    // Cannot make a booking
    private void adminSetUp()
    {
        changeTXT.setText("Welcome Admin! Choose a date to view desk allocation");
        cancel.setVisible(false);
        submitter.setVisible(false);
        booking.setVisible(false);
        HeaderTXT.setText("View Desk allocation");
    }

    // Turn off bookings
    private void disableBooking()
    {
        datePicker.setMouseTransparent(true);
        datePicker.setDisable(true);
        submitter.setVisible(false);
    }

    // Go back
    public void back(ActionEvent event) throws IOException {
        Parent createAccParent;
        if(UserHolder.getInstance().getUser().getAdmin())
        {
            createAccParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../ui/AdminReport.fxml")));
        }
        else
        {
            createAccParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../ui/EmployeeHomepage.fxml")));
        }
        Stage newStage = new Stage();
        newStage.setResizable(false);
        newStage.setScene(new Scene(createAccParent));
        newStage.show();

        final Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    // Highlight a table and reset the rest
    public void highlight(MouseEvent event) {
        if(!UserHolder.getInstance().getUser().getAdmin())
        {
            Rectangle rectangle = (Rectangle) event.getSource();
            setTables();
            rectangle.setFill(Color.rgb(92,121,239));
            rectangle.setStroke(Color.WHITE);
            rectangle.strokeWidthProperty().set(3);
            currentRectangle = rectangle;
        }
    }

    // Submit button to book a table
    public void submit(ActionEvent event) {
        if(currentRectangle != null)
        {
            for(int i = 0; i < rectangles.size(); i++)
            {
                if (currentRectangle == rectangles.get(i))
                {
                    int desk = i+1;
                    String contentText = "Are you sure you want to book Desk " + desk +"?";

                    if(currentUserBooking != null)
                    {
                         contentText += "\n\nThis will cancel your booking for " +
                                 currentUserBooking.getTableNumber() + " on the " +
                                 currentUserBooking.getDate().toString() + "!";
                    }
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText(contentText);
                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.get() == ButtonType.OK)
                    {
                        if(currentUserBooking != null)
                        {
                            makeModel.deleteBooking(currentUserBooking);
                            currentUserBooking = null;
                        }
                        makeModel.makeBooking(i+1, date);
                        refresh(event);
                    }
                }
            }
        }
    }

    // Update all rectangles the datePicker is changed
    public void datePicker(ActionEvent event)
    {
        currentRectangle = null;
        LocalDate datePicked = datePicker.getValue();
        int day = datePicked.getDayOfYear();
        int year = datePicked.getYear();
        LocalDate dateNow = LocalDate.now();
        int currentDay = dateNow.getDayOfYear();
        int currentYear = dateNow.getYear();

        date = datePicker.getValue();

        if(year > currentYear || (year == currentYear && day > currentDay)) {

            currentBookings = makeModel.getTableAvailability(date);
            setTables();
        }
        else {
            setAllTab(Color.GRAY);
            for(int i =0; i < rectangles.size(); i++)
            {
                rectangles.get(i).setMouseTransparent(true);
            }
        }
    }

    // set the tables by availability
    public void setTables(){
        setAllTab(Color.rgb(169,184,243));

        for(int i = 0; i < rectangles.size(); i++)
        {
            rectangles.get(i).setMouseTransparent(false);
        }
        for(int i = 0; i < currentBookings.size(); i++)
        {
            int deskid = currentBookings.get(i).getTableNumber();

            rectangles.get(deskid - 1).setFill(Color.GREY);
            rectangles.get(deskid - 1).setMouseTransparent(true);
        }
    }

    // Set rectangles to the given colour
    public void setAllTab(Color colour)
    {
        for(int i = 0; i < rectangles.size(); i++)
        {
            rectangles.get(i).setFill(colour);
            rectangles.get(i).strokeWidthProperty().set(0);
        }
    }

    // Cancel a booking
    public void cancel(ActionEvent event)
    {
        if(makeModel.deleteBooking(currentUserBooking))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Successfully deleted");
            refresh(event);
        }
    }

    // Refresh the page
    public void refresh(ActionEvent event){
        try {
            Parent createAccParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../ui/ManageBooking.fxml")));
            Stage newStage = new Stage();
            newStage.setResizable(false);
            newStage.setScene(new Scene(createAccParent));
            newStage.show();

            final Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.close();
        } catch (Exception e)
        {
            System.out.println(e);
        }
    }
}
