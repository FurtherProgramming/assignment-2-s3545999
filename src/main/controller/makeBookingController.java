package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import main.Booking;
import main.model.makeBookingModel;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class makeBookingController implements Initializable {
    public makeBookingModel makeModel = new makeBookingModel();

    private List<Rectangle> rectangles = new ArrayList<>();
    private Rectangle currentRectangle;
    private LocalDate date;

    List<Booking> currentBookings;
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
    Button cancel;
    @FXML
    Button submitter;

    public void initialize(URL location, ResourceBundle resources){
        rectangles.add(Table1);
        rectangles.add(Table2);
        rectangles.add(Table3);
        rectangles.add(Table4);
        rectangles.add(Table5);
        rectangles.add(Table6);
        rectangles.add(Table7);
        rectangles.add(Table8);
        setAllTab(Color.GRAY);

        for(int i = 0; i < rectangles.size(); i++)
        {
            rectangles.get(i).setMouseTransparent(true);
        }

        if(!makeModel.checkBooking(LocalDate.now()))
        {
            cancel.setVisible(false);
            booking.setVisible(false);
            changeTXT.setText("You don't have a Booking! Do you want to make one?");
        }
        else
        {
            String theString = makeModel.getBooking();
            booking.setText(theString);
        }

        if(makeModel.checkBooking(LocalDate.now()) && !makeModel.checkBooking(LocalDate.now().plusDays(2)))
        {
            datePicker.setMouseTransparent(true);
            datePicker.setDisable(true);
            submitter.setVisible(false);
            changeTXT.setText("You must cancel your booking before rebooking!");
        }
    }

    public void back(ActionEvent event) throws IOException {

        Parent createAccParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../ui/EmployeeHomepage.fxml")));
        Stage newStage = new Stage();
        newStage.setScene(new Scene(createAccParent, 600, 400));
        newStage.show();

        final Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    public void highlight(MouseEvent event) {
        Rectangle rectangle = (Rectangle) event.getSource();
        setTables();
        rectangle.setFill(Color.BLUE);
        currentRectangle = rectangle;
    }

    public void submit(ActionEvent event) throws IOException {
        if(currentRectangle != null)
        {
            for(int i = 0; i < rectangles.size(); i++)
            {
                if (currentRectangle == rectangles.get(i))
                {
                    makeModel.makeBooking(i+1, date);
                    refresh(event);
                }
            }
        }
    }

    public void datePicker(ActionEvent event)
    {
        currentRectangle = null;
        LocalDate datePicked = datePicker.getValue();
        datePicked.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
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
            setAllTab(Color.RED);
            for(int i =0; i < rectangles.size(); i++)
            {
                rectangles.get(i).setMouseTransparent(true);
            }
        }
    }

    public void setTables(){
        setAllTab(Color.GREEN);
        for(int i = 0; i < rectangles.size(); i++)
        {
            rectangles.get(i).setMouseTransparent(false);
        }
        for(int i = 0; i < currentBookings.size(); i++)
        {
            int deskid = currentBookings.get(i).getTableNumber();

            rectangles.get(deskid - 1).setFill(Color.GREY);
        }
    }

    public void setAllTab(Color colour)
    {
        for(int i = 0; i < rectangles.size(); i++)
        {
            rectangles.get(i).setFill(colour);
        }
    }

    public void cancel(ActionEvent event)
    {
        if(makeModel.cancelBooking())
        {
            System.out.println("Success");
            refresh(event);
        }
    }

    public void refresh(ActionEvent event){
        try {
            Parent createAccParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../ui/ManageBooking.fxml")));
            Stage newStage = new Stage();
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
