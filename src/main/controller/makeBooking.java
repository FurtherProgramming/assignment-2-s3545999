package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
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

import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class makeBooking implements Initializable {
    public makeBookingModel makeModel = new makeBookingModel();

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
        setAllTab("RED");
    }

    public void back(ActionEvent event) throws IOException {

        Parent createAccParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../ui/employeeHomepage.fxml")));
        Stage newStage = new Stage();
        newStage.setScene(new Scene(createAccParent, 600, 400));
        newStage.show();

        final Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    public void highlight(ActionEvent event) throws IOException {
        Object object = event.getSource();
        Rectangle rec = (Rectangle) object;
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

        java.sql.Date date = java.sql.Date.valueOf(datePicker.getValue());
        System.out.println("Checking desks database");
        if(year > currentYear || (year == currentYear && day > currentDay)) {
            System.out.println("Checking correct date");
            setTables(date);
        }
    }

    public void setTables(java.sql.Date date){
        List<Desk> currentDesks = makeModel.getTableAvailability(date);
        setAllTab("GREEN");
        for(int i = 0; i < currentDesks.size(); i++)
        {
            System.out.println("Setting Color");
            System.out.println(currentDesks.get(i).getDeskID());
            int deskid = currentDesks.get(i).getDeskID();

            if (deskid == 1)
            {
                Table1.setFill(Color.RED);
                Table1.setMouseTransparent(true);
            }
            else if (deskid == 2)
            {
                Table2.setFill(Color.RED);
                Table2.setMouseTransparent(true);
            }
            else if (deskid == 3)
            {
                Table3.setFill(Color.RED);
                Table3.setMouseTransparent(true);
            }
            else if (deskid == 4)
            {
                Table4.setFill(Color.RED);
                Table4.setMouseTransparent(true);
            }
            else if (deskid == 5)
            {
                Table5.setFill(Color.RED);
                Table5.setMouseTransparent(true);
            }
            else if (deskid == 6)
            {
                Table6.setFill(Color.RED);
                Table6.setMouseTransparent(true);
            }
            else if (deskid == 7)
            {
                Table7.setFill(Color.RED);
                Table7.setMouseTransparent(true);
            }
            else if (deskid == 8)
            {
                Table8.setFill(Color.RED);
                Table8.setMouseTransparent(true);
            }
        }
    }

    public void setAllTab(String Colour)
    {
        if(Colour.equals("RED")) {
            Table1.setFill(Color.RED);
            Table2.setFill(Color.RED);
            Table3.setFill(Color.RED);
            Table4.setFill(Color.RED);
            Table5.setFill(Color.RED);
            Table6.setFill(Color.RED);
            Table7.setFill(Color.RED);
            Table8.setFill(Color.RED);
        }
        else if(Colour.equals("GREEN")) {
            Table1.setFill(Color.GREEN);
            Table2.setFill(Color.GREEN);
            Table3.setFill(Color.GREEN);
            Table4.setFill(Color.GREEN);
            Table5.setFill(Color.GREEN);
            Table6.setFill(Color.GREEN);
            Table7.setFill(Color.GREEN);
            Table8.setFill(Color.GREEN);
        }
    }
}
