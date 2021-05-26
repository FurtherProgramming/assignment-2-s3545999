package main.controller;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import main.Desk;
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
    private String date = "";

    List<Desk> currentDesks;
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

    public void initialize(URL location, ResourceBundle resources){
        rectangles.add(Table1);
        rectangles.add(Table2);
        rectangles.add(Table3);
        rectangles.add(Table4);
        rectangles.add(Table5);
        rectangles.add(Table6);
        rectangles.add(Table7);
        rectangles.add(Table8);
        setAllTab(Color.RED);
        for(int i = 0; i<rectangles.size(); i++)
        {
            rectangles.get(i).setMouseTransparent(true);
        }
    }

    public void back(ActionEvent event) throws IOException {

        Parent createAccParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../ui/employeeHomepage.fxml")));
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

    public void submit(ActionEvent event)
    {
        if(currentRectangle != null)
        {
            for(int i = 0; i < rectangles.size(); i++)
            {
                if (currentRectangle == rectangles.get(i))
                {
                    makeModel.makeBooking(i+1, date);
                }
            }
        }

    }

    public void datePicker(ActionEvent event)
    {
        LocalDate datePicked = datePicker.getValue();
        datePicked.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
        int day = datePicked.getDayOfYear();
        int year = datePicked.getYear();
        LocalDate dateNow = LocalDate.now();
        int currentDay = dateNow.getDayOfYear();
        int currentYear = dateNow.getYear();

        java.sql.Date theDate = java.sql.Date.valueOf(datePicker.getValue());
        System.out.println("Checking desks database");
        if(year > currentYear || (year == currentYear && day > currentDay)) {
            System.out.println("Checking correct date");
            date = theDate.toString();
            currentDesks = makeModel.getTableAvailability(date);
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
        for(int i = 0; i < currentDesks.size(); i++)
        {
            System.out.println("Setting Color");
            System.out.println(currentDesks.get(i).getDeskID());
            int deskid = currentDesks.get(i).getDeskID();

            rectangles.get(deskid - 1).setFill(Color.RED);

        }
    }

    public void setAllTab(Color colour)
    {
        for(int i = 0; i < rectangles.size(); i++)
        {
            rectangles.get(i).setFill(colour);
        }
    }
}
