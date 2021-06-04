package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.model.CheckinModel;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CheckinController implements Initializable {

    CheckinModel checkinModel = new CheckinModel();

    @FXML
    Label bookingTXT;
    @FXML
    Label bookingToday;
    @FXML
    Button checkInButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(!checkinModel.checkBookingToday())
        {
            bookingToday.setText("You do not have a booking today!");
            bookingTXT.setVisible(false);
            checkInButton.setVisible(false);
        }
    }

    public void back(ActionEvent event) throws IOException {

        Parent createAccParent = FXMLLoader.load(getClass().getResource("../ui/Welcome.fxml"));
        Stage newStage = new Stage();
        newStage.setScene(new Scene(createAccParent, 600, 400));
        newStage.show();

        final Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    public void CheckIn(ActionEvent event) throws IOException {

    }


}
