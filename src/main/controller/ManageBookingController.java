package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.Booking;
import main.User;
import main.UserHolder;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManageBookingController implements Initializable {

    private ArrayList<Booking> theBookings;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void back(ActionEvent event) throws IOException {

        Parent createAccParent = FXMLLoader.load(getClass().getResource("../ui/AdminHomepage.fxml"));
        Stage newStage = new Stage();
        newStage.setScene(new Scene(createAccParent, 600, 400));
        newStage.show();

        final Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    public void fillTable()
    {
        User user = UserHolder.getInstance().getUser();
        // Go to Cancel Booking Model and get all users Bookings
    }
}
