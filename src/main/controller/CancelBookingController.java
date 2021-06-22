package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.Booking;
import main.User;
import main.UserHolder;
import main.model.cancelBookingModel;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CancelBookingController implements Initializable {

    cancelBookingModel cancelBooking = new cancelBookingModel();

    @FXML
    Button cancel;

    @FXML
    Label booking;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(!cancelBooking.checkBooking())
        {
            cancel.setDisable(true);
            cancel.setMouseTransparent(true);
            booking.setText("You do not have a booking!");
        }
        else
        {
            String theString = cancelBooking.getBooking();
            booking.setText(theString);
        }
    }

    public void back(ActionEvent event) throws IOException {

        Parent createAccParent = FXMLLoader.load(getClass().getResource("../ui/EmployeeHomepage.fxml"));
        Stage newStage = new Stage();
        newStage.setScene(new Scene(createAccParent, 600, 400));
        newStage.show();

        final Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    public void cancel(ActionEvent event)
    {
        if(cancelBooking.cancelBooking())
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Booking Cancelled!");
            alert.show();
        }
    }
}
